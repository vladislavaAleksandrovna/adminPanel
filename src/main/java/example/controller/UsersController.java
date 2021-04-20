package example.controller;

import example.model.User;
import example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/users")
public class UsersController {

    private UserService userService;
    private String[] arrayColor = {"red", "blue", "orange", "green", "purple", "black", "yellow"};

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    /**
     * @param user
     * @return login page
     */

    @GetMapping
    public String showFormUser(@ModelAttribute("user") User user) {
        return "users/show";
    }

    /**
     * @param user
     * @return the registration page for a new user
     */

    @GetMapping("/new")
    public String showFormNew(@ModelAttribute("user") User user) {
        return "users/new";
    }

    /**
     * @param user
     * @param bindingResult
     * @return  the page where there will be a link to the main login page
     */

    @PostMapping("/new")
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "users/new";
        } else if (userService.isExist(user.getLogin())) {
            return "users/hasAlready";
        }
        user.setRole("user");
        user.setColor("red");
        userService.create(user);
        return "users/passedRegistr";
    }

    /**
     * @param user
     * @param bindingResult
     * @param request
     * @return  the page where we change the color for the user or the page for the admin
     */


    @PostMapping
    public String getLoginPage(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "users/show";
        } else if (userService.isExist(user.getLogin(), user.getPassword())) {
            if (userService.getRoleUser(user.getLogin()).equals("admin")) {
                String color = userService.getColorUser(user.getLogin());
                List<User> users = userService.findAll();
                HttpSession session = request.getSession();
                session.setAttribute("login", user.getLogin());
                session.setMaxInactiveInterval(30);
                user.setColor(color);
                user.setArray(arrayColor);
                user.setUsers(users);
                return "users/colorAdmin";
            }
            String color = userService.getColorUser(user.getLogin());
            HttpSession session = request.getSession();
            session.setAttribute("login", user.getLogin());
            session.setMaxInactiveInterval(30);
            user.setColor(color);
            user.setArray(arrayColor);
            return "users/color";
        }
        return "users/password";
    }

    /**
     * @param user
     * @param request
     * @return  page with updated color
     */
    @PatchMapping("/color")
    public String updateColor(@ModelAttribute("user") User user, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            String[] arrayColorUser = user.getArray();
            String color = Arrays.stream(arrayColorUser).findAny().get();
            String login = (String) session.getAttribute("login");
            userService.update(login, color);
            user.setArray(arrayColor);
            user.setColor(color);
            return "users/color";
        }
        return "users/session";
    }

    /**
     * @param user
     * @param request
     * @return  page with updated color for admin
     */
    @PatchMapping("/colorAdmin")
    public String updateColorAdmin(@ModelAttribute("user") User user, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            String[] arrayColorUser = user.getArray();
            String color = Arrays.stream(arrayColorUser).findAny().get();
            String login = (String) session.getAttribute("login");
            userService.update(login, color);
            user.setArray(arrayColor);
            user.setColor(color);
            List<User> users = userService.findAll();
            user.setUsers(users);
            return "users/colorAdmin";
        }
        return "users/session";
    }

    /**
     * @param user
     * @param request
     * @return  the updated page without users who have been deleted
     */

    @DeleteMapping("/color/admin/{id}")
    public String deleteUser(@ModelAttribute("user") User user, @PathVariable("id") Integer id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            userService.delete(id);
            user.setArray(arrayColor);
            String login = (String) session.getAttribute("login");
            String color = userService.getColorUser(login);
            user.setColor(color);
            List<User> users = userService.findAll();
            user.setUsers(users);
            return "users/colorAdmin";
        }
        return "users/session";
    }

    /**
     *
     * @param user
     * @param request
     * @return main login page after logout
     */
    @PostMapping("/color/exit")
    public String exit(@ModelAttribute("user") User user, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            return "users/show";
        }
        return "users/session";
    }
}

