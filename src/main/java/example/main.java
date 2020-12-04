package example;

import example.dao.UserDao;
import example.dao.UserDaoImpl;
import example.model.User;

public class main {
    public static void main(String[] args) {
        UserDao userDao= new UserDaoImpl();
        User user = new User();
        user.setLogin("r");
        user.setPassword("rrr");
        user.setColor("red");
        user.setEmail("ffd@hfh.com");
        user.setRole("user");
        userDao.create(user);
    }
}
