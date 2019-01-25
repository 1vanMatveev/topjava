package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            mealRestController.getAll().forEach(System.out::println);
            System.out.println();
            mealRestController.getFiltered("11:00","","","").forEach(System.out::println);
            System.out.println();
            System.out.println(mealRestController.get(4).toString());
            System.out.println();
            mealRestController.create(new Meal(LocalDateTime.parse("2018-01-19 10:10", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),"Завтрак",500,2));
            mealRestController.getAll().forEach(System.out::println);
            System.out.println();
            mealRestController.update(new Meal(LocalDateTime.parse("2018-01-19 15:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),"Обедик",500,2),1);
            mealRestController.getAll().forEach(System.out::println);
            System.out.println();
            mealRestController.delete(3);
            mealRestController.getAll().forEach(System.out::println);
            System.out.println();
            mealRestController.getFiltered("","","2018-01-01","").forEach(System.out::println);

        }
    }
}
