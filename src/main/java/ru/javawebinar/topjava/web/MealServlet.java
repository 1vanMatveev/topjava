package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.repository.MemoryRepository;
import ru.javawebinar.topjava.repository.Repository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private Repository repository = new MemoryRepository();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Action action = getAction(req);
        Meal meal;

        switch (action.name) {
            case "update":
                log.debug("redirect to editMeal (update)");
                req.setAttribute("action_name","Update");
                meal = repository.get(action.id);
                req.setAttribute("meal",meal);
                req.getRequestDispatcher("/editMeal.jsp").forward(req,resp);
                break;
            case "new":
                log.debug("redirect to editMeal (new)");
                req.setAttribute("action_name","New");
                meal = new Meal(0,LocalDateTime.now().withNano(0).withSecond(0),"",0);
                req.setAttribute("meal",meal);
                req.getRequestDispatcher("/editMeal.jsp").forward(req,resp);
                break;
            case "remove":
                log.debug("remove meal with id = " + action.id);
                repository.remove(action.id);
                doGet(req,resp);
                break;
            case "save":
                log.debug("save meal");
                meal = new Meal(action.id,action.dateTime,action.description,action.calories);
                repository.save(meal);
                doGet(req,resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to meals");

        List<MealWithExceed> meals = MealsUtil.getWithExceeded(repository.getAll(),MealsUtil.defaultCalories);
        req.setAttribute("meals",meals);
        
        req.getRequestDispatcher("/meals.jsp").forward(req,resp);
    }

    private class Action{
        String name;
        int id;
        String description;
        LocalDateTime dateTime;
        int calories;
    }

    private Action getAction(HttpServletRequest req) throws IOException{
        Enumeration<String> parameterNames = req.getParameterNames();
        String param = "";
        while (parameterNames.hasMoreElements()){
            param = parameterNames.nextElement();
            if (param.startsWith("action")) break;
        }
        if (param.equals("")) throw new IOException("Не найден параметр action");
        Action action = new Action();
        if (param.startsWith("action_update") ){
            action.id = Integer.parseInt(param.substring(param.lastIndexOf("_")+1));
            action.name = "update";

        }else if(param.startsWith("action_remove")){
            action.id = Integer.parseInt(param.substring(param.lastIndexOf("_")+1));
            action.name = "remove";
        }else if(param.startsWith("action_save")){
            action.name = "save";
            action.id = Integer.parseInt(req.getParameter("id"));
            action.description = req.getParameter("description");
            action.dateTime = LocalDateTime.parse(req.getParameter("dateTime"));
            action.calories = Integer.parseInt(req.getParameter("calories"));
        }else if(param.startsWith("action_new")){
            action.name = "new";
        }else{
            throw new IOException("Не верное значение параметра action");
        }
        return action;
    }
}
