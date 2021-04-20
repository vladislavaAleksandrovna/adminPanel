package example.service;

import example.model.User;

public interface UserService extends CrudService<User> {
    String getColorUser(String login);

    String getRoleUser(String login);

    String getEmailUser(String login);

//    Integer getId(String login);

    boolean isExist(String login, String password);

    boolean isExist(String login);


}
