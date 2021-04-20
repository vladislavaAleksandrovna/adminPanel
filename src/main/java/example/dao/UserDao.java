package example.dao;

import example.model.User;

public interface UserDao extends CrudDao<User> {

    String getColorUser(String login);

    String getRoleUser(String login);

    String getEmailUser(String login);

    boolean isExist(String login, String password);

    boolean isExist(String login);

}
