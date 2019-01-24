package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MemoryRepository;
import ru.javawebinar.topjava.repository.Repository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private Repository repository = new MemoryRepository();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        int id = getId(req);
        log.info(id != 0 ? "Save meal with id = {}" : "Save new meal", id);
        repository.save(new Meal(id,
                                 LocalDateTime.parse(req.getParameter("dateTime")),
                                 req.getParameter("description"),
                                 Integer.parseInt(req.getParameter("calories"))));
        resp.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        action = action == null ? "all" : action;
        int id;
        switch (action){
            case "remove":
                id = getId(req);
                log.info("Remove meal with id = {}", id);
                repository.remove(id);
                resp.sendRedirect("meals");
                break;
            case "update":
                id = getId(req);
                log.info("Edit meal with id = {}", id);
                req.setAttribute("meal",repository.get(id));
                req.setAttribute("title", "Edit meal");
                req.getRequestDispatcher("/mealForm.jsp").forward(req,resp);
                break;
            case "new":
                log.info("Create new meal");
                req.setAttribute("meal", new Meal(0,LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),"",null));
                req.setAttribute("title", "Create meal");
                req.getRequestDispatcher("/mealForm.jsp").forward(req,resp);
                break;
            case "all":
            default:
                log.info("Get meal list");
                req.setAttribute("meals",MealsUtil.getWithExceeded(repository.getAll(),MealsUtil.DEFAULT_CALORIES_PER_DAY));
                req.getRequestDispatcher("/meals.jsp").forward(req,resp);
                break;
        }
    }

    private int getId(HttpServletRequest req){
        Objects.requireNonNull(req.getParameter("id"));
        return Integer.parseInt(req.getParameter("id"));
    }

}
