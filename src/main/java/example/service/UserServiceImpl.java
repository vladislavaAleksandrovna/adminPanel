package example.service;

import example.dao.UserDao;
import example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;


    @Transactional
    @Override
    public String getColorUser(String login) {
        return userDao.getColorUser(login);
    }

    @Transactional
    @Override
    public String getRoleUser(String login) {
        return userDao.getRoleUser(login);
    }

    @Transactional
    @Override
    public String getEmailUser(String login) {
        return userDao.getEmailUser(login);
    }

//    @Override
//    public Integer getId(String login) {
//        return userDao.getId(login);
//    }

    @Transactional
    @Override
    public boolean isExist(String login, String password) {
        return userDao.isExist(login, password);
    }

    @Transactional
    @Override
    public boolean isExist(String login) {
        return userDao.isExist(login);
    }

//    @Override
//    public Integer findById(Integer id) {
//        return userDao.findById(id);
//    }

    @Transactional
    @Override
    public void create(User model) {
        userDao.create(model);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        userDao.delete(id);
    }

    @Transactional
    @Override
    public void update(String login, String color) {
        userDao.update(login, color);
    }

    @Transactional
    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }
}
