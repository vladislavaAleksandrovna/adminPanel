package example.controllers;

import example.dao.UserDao;
import example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UsersController {
    private final UserDao userDao;

    private HttpSession session;


    @Autowired
    public UsersController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping
    public String showFormUser(@ModelAttribute("user") User user) {
        return "users/show";
    }

    @GetMapping("/new")
    public String showFormNew(@ModelAttribute("user") User user) {
        return "users/new";
    }

    @PostMapping("/new")
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            if(user.getEmail()==""){
                boolean state=true;
                user.setBol(state);
            }
            return "users/new";
        } else if (userDao.isExists(user.getLogin())) {
            return "users/hasAlready";
        } else if (user.getEmail() != "") {
            user.setRole("user");
            user.setColor("red");
            userDao.create(user);
            return "users/passedRegistr";
        }
        boolean state=true;
        user.setBol(state);
        return "users/new";
    }

    @PostMapping
    public String loginPage(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return "users/show";
        } else if (userDao.isExists(user.getLogin(),user.getPassword())) {
            if (userDao.getRoleUser(user.getLogin()).equals("admin")) {
                String login = user.getLogin();
                String color = userDao.getColorUser(login);
                String[] array = {"red", "blue", "orange", "purple", "green"};
                List<User> users = userDao.findAll();
                session = request.getSession();
                session.setAttribute("login", login);
                session.setMaxInactiveInterval(30);
                user.setColor(color);
                user.setArray(array);
                user.setUsers(users);
                return "users/colorAdmin";
            }
            String login = user.getLogin();
            String color = userDao.getColorUser(login);
            String[] array = {"red", "blue", "orange", "purple", "green"};
            session = request.getSession();
            session.setAttribute("login", login);
            session.setMaxInactiveInterval(30);
            user.setColor(color);
            user.setArray(array);
            return "users/color";

        }
        return "users/password";
    }


    @PostMapping("/color")
    public String colorPage(@ModelAttribute("user") User user, HttpServletRequest request) {
        session = request.getSession(false);
        if (session != null) {
            String[] array = user.getArray();
            String color = Arrays.stream(array).findAny().get();
            String login = (String) session.getAttribute("login");
            userDao.update(login, color);
            array = new String[]{"red", "blue", "orange", "purple", "green"};
            user.setArray(array);
            user.setColor(color);
            return "users/color";
        }
        return "users/session";
    }

    @PostMapping("/colorAdmin")
    public String colorPageAdmin(@ModelAttribute("user") User user, HttpServletRequest request) {
        session = request.getSession(false);
        if (session != null) {
            String[] array = user.getArray();
            String color = Arrays.stream(array).findAny().get();
            String login = (String) session.getAttribute("login");
            userDao.update(login, color);
            array = new String[]{"red", "blue", "orange", "purple", "green"};
            user.setArray(array);
            user.setColor(color);
            List<User> users = userDao.findAll();
            user.setUsers(users);
            return "users/colorAdmin";
        }
        return "users/session";
    }

    @DeleteMapping("/color/admin")
    public String delete(@ModelAttribute("user")User user, HttpServletRequest request) {
        session = request.getSession(false);
        Integer id = user.getId();
        if(userDao.findById(id).equals(id) && session!=null &&id!=0){
            userDao.delete(id);
            String[] array = new String[]{"red", "blue", "orange", "purple", "green"};
            String login = (String) session.getAttribute("login");
            String color = userDao.getColorUser(login);
            user.setArray(array);
            user.setColor(color);
            List<User> users = userDao.findAll();
            user.setUsers(users);
            return "users/colorAdmin";
        }
        else if(session!=null&&!userDao.findById(id).equals(id)|| id==0){
            boolean state = true;
            String[] array = new String[]{"red", "blue", "orange", "purple", "green"};
            String login = (String) session.getAttribute("login");
            String color = userDao.getColorUser(login);
            List<User> users = userDao.findAll();
            user.setArray(array);
            user.setColor(color);
            user.setBol(state);
            user.setUsers(users);
            return "users/colorAdmin";
        }
        return "users/session";
    }
}

