package example.service;

import java.util.List;

public interface CrudService<T> {
//    Integer findById (Integer id);

    void create(T model);

    void delete(Integer id);

    void update(String login, String color);

    List<T> findAll();
}

