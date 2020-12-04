package example.dao;

import example.model.User;
import example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Stream;

@Component
public class UserDaoImpl implements UserDao {

    private SessionFactory sessionFactory;

    public UserDaoImpl() {
        sessionFactory = HibernateUtil.getSessionFactory();

    }

    @Override
    public boolean isExists(String login) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        Query query = session.createQuery(cq);
        List<User> userEntityList = query.getResultList();
        boolean result = userEntityList.stream().anyMatch(user -> user.getLogin().equals(login));
        session.close();
        return result;
    }

    @Override
    public void create (User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(Integer id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        User userEntity = session.get(User.class, id);
        session.delete(userEntity);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(String login, String color) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        Query query = session.createQuery(cq);
        List<User> userEntityList = query.getResultList();
        User userEntity = userEntityList.stream().filter(user -> user.getLogin().equals(login)).findAny().get();
        userEntity.setColor(color);
        session.beginTransaction();
        session.update(color,userEntity);
        session.getTransaction().commit();
        session.close();
    }


    @Override
    public boolean isExists(String login,String password) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        Query query = session.createQuery(cq);
        List<User> userEntityList = query.getResultList();
        User userEmpty = new User();
        String passwordUser = userEntityList.stream().filter(user->user.getLogin().equals(login)).findAny().orElse(userEmpty).getPassword();
        boolean result = false;
        if(passwordUser!=null) {
            if (passwordUser.equals(password)) {
                return result = true;
            }
        }
        session.close();
        return result;
    }

    @Override
    public String getColorUser(String login) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        Query query = session.createQuery(cq);
        List<User> userEntityList = query.getResultList();
        String color = userEntityList.stream().filter(user->user.getLogin().equals(login)).findAny().get().getColor();
        session.close();
        return color;
    }
    @Override
    public String getEmailUser(String login){
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        Query query = session.createQuery(cq);
        List<User> userEntityList = query.getResultList();
        User userEmpty = new User();
        String email = userEntityList.stream().filter(user->user.getLogin().equals(login)).findAny().orElse(userEmpty).getEmail();
        session.close();
        return email;
    }

    @Override
    public List<User> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        cq.select(root);
        Query query = session.createQuery(cq);
        List<User> userEntityList = query.getResultList();
        session.close();
        return userEntityList;
    }

    @Override
    public String getRoleUser(String login) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        Query query = session.createQuery(cq);
        List<User> userEntityList = query.getResultList();
        String roleUser = userEntityList.stream().filter(user -> user.getLogin().equals(login)).findAny().get().getRole();
        session.close();
        return roleUser;
    }


    @Override
    public Integer findById(Integer id) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        Query query = session.createQuery(cq);
        List<User> userEntityList = query.getResultList();
        User userEmpty = new User();
        Integer idUser = userEntityList.stream().filter(user -> user.getId().equals(id)).findAny().orElse(userEmpty).getId();
        session.close();
        return idUser;
    }

    public Integer getId (String login) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        Query query = session.createQuery(cq);
        List<User> userEntityList = query.getResultList();
        User userEmpty = new User();
        Integer id = userEntityList.stream().filter(user -> user.getLogin().equals(login)).findAny().orElse(userEmpty).getId();
        session.close();
        return id;
    }
}