package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepositoryImpl implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private static final ResultSetExtractor<List<User>> RESULT_SET_EXTRACTOR = resultSet -> {
        Map<Integer, User> users = new HashMap<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            Role role = Role.valueOf(resultSet.getString("role"));
            if (users.computeIfPresent(id,(i,oldUser) -> {oldUser.addRole(role); return oldUser;}) == null){
                User user = ROW_MAPPER.mapRow(resultSet,id);
                user.setRoles(EnumSet.of(role));
                users.put(id,user);
            }
        }
        return List.copyOf(users.values()).stream().sorted(Comparator.comparing(User::getName).thenComparing(User::getEmail)).collect(Collectors.toList());
    };

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
        } else {
            if(namedParameterJdbcTemplate.update(
                    "UPDATE users SET name=:name, email=:email, password=:password, " +
                            "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", parameterSource) == 0){
                return null;
            };
            jdbcTemplate.update("delete from user_roles where user_id=?", user.getId());
        }
        List<Role> roles = new ArrayList<>(user.getRoles());
        jdbcTemplate.batchUpdate("insert into user_roles (user_id, role) VALUES (?,?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                Role role = roles.get(i);
                preparedStatement.setInt(1, user.getId());
                preparedStatement.setString(2, role.toString());
            }

            @Override
            public int getBatchSize() {
                return roles.size();
            }
        });
        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT id, name, email, password, registered, enabled, calories_per_day, role " +
                "                                   FROM users left join user_roles on id = user_id WHERE id=?", RESULT_SET_EXTRACTOR, id);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        List<User> users = jdbcTemplate.query("SELECT id, name, email, password, registered, enabled, calories_per_day, role " +
                "FROM users left join user_roles on id = user_id WHERE email=?", RESULT_SET_EXTRACTOR, email);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT id, name, email, password, registered, enabled, calories_per_day, role FROM users " +
                "left join user_roles on id = user_id", RESULT_SET_EXTRACTOR);
    }
}
