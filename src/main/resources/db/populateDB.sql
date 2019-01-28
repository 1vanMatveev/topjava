DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;
ALTER SEQUENCE meal_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);


INSERT into meals (datetime, description, calories, user_id) values
('2019-01-02 10:00','Завтрак',500,100000),
('2019-01-02 14:00','Обед',1000,100000),
('2019-01-02 18:00','Ужин',500,100000),
('2019-01-03 10:00','Завтрак',500,100000),
('2019-01-03 14:00','Обед',1000,100000),
('2019-01-03 18:00','Ужин',510,100000);

