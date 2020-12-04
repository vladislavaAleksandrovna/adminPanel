package example.dao;

import java.util.List;

public interface CrudDao<T> {

    Integer findById (Integer id);

    void create(T model);

    void delete(Integer id);

    void update(String login, String color);

    boolean isExists(String login, String password);

    boolean isExists(String login);

    List<T> findAll();
}

