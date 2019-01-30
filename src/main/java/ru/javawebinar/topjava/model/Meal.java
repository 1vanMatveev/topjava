package ru.javawebinar.topjava.model;

//import org.springframework.format.annotation.DateTimeFormat;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@NamedQueries({
        @NamedQuery(name = Meal.DELETE, query = "delete from Meal m where m.id=:id and m.user.id=:user_id"),
        @NamedQuery(name = Meal.ALL, query = "select m from Meal m where m.user.id = ?1 order by m.dateTime desc"),
        @NamedQuery(name = Meal.ALL_FILTERED,
                query = "select m from Meal m where m.user.id =?1 and m.dateTime >=?2 and m.dateTime <=?3 order by m.dateTime desc"),
        @NamedQuery(name = Meal.GET, query = "select m from Meal m where m.id=:id and m.user.id=:user_id")
})
@Entity
@Table(name = "meals", uniqueConstraints = {@UniqueConstraint(columnNames = {"date_time","user_id"})})
public class Meal extends AbstractBaseEntity {

    public static final String ALL = "Meal.all";
    public static final String ALL_FILTERED = "Meal.allFiltered";
    public static final String DELETE = "Meal.delete";
    public static final String GET = "Meal.get";

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "description", nullable = false)
    @NotBlank
    @Size(max = 100)
    private String description;

    @Column(name = "calories")
    @Range(min = 1, max = 30000)
    private int calories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Meal() {
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
