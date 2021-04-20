package example.dao;

import example.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Stream;

@Repository
public class UserDaoImpl implements UserDao {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean isExist(String login) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        Query query = session.createQuery(cq);
        List<User> userEntityList = query.getResultList();
        boolean result = userEntityList.stream().anyMatch(user -> user.getLogin().equals(login));
        return result;
    }

    @Override
    public void create(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.save(user);
    }

    @Override
    public void delete(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        User userEntity = session.get(User.class, id);
        session.delete(userEntity);
    }

    @Override
    public void update(String login, String color) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        Query query = session.createQuery(cq);
        List<User> userEntityList = query.getResultList();
        User userEntity = userEntityList.stream().filter(user -> user.getLogin().equals(login)).findAny().get();
        userEntity.setColor(color);
        session.update(color, userEntity);
    }


    @Override
    public boolean isExist(String login, String password) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        Query query = session.createQuery(cq);
        List<User> userEntityList = query.getResultList();
        User userEmpty = new User();
        String passwordUser = userEntityList.stream().filter(user -> user.getLogin().equals(login)).findAny().orElse(userEmpty).getPassword();
        boolean result = false;
        if (passwordUser != null) {
            if (passwordUser.equals(password)) {
                return result = true;
            }
        }
        return result;
    }

    @Override
    public String getColorUser(String login) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        Query query = session.createQuery(cq);
        List<User> userEntityList = query.getResultList();
        String color = userEntityList.stream().filter(user -> user.getLogin().equals(login)).findAny().get().getColor();
        return color;
    }

    @Override
    public String getEmailUser(String login) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        Query query = session.createQuery(cq);
        List<User> userEntityList = query.getResultList();
        User userEmpty = new User();
        String email = userEntityList.stream().filter(user -> user.getLogin().equals(login)).findAny().orElse(userEmpty).getEmail();
        return email;
    }

    @Override
    public List<User> findAll() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        cq.select(root);
        Query query = session.createQuery(cq);
        List<User> userEntityList = query.getResultList();
        return userEntityList;
    }

    @Override
    public String getRoleUser(String login) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        Query query = session.createQuery(cq);
        List<User> userEntityList = query.getResultList();
        String roleUser = userEntityList.stream().filter(user -> user.getLogin().equals(login)).findAny().get().getRole();
        return roleUser;
    }
}