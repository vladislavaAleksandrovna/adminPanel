package example.dao;

import java.util.List;

public interface CrudDao<T> {

    void create(T model);

    void delete(Integer id);

    void update(String login, String color);

    List<T> findAll();
}

