package example;

import example.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyMethod {
    public static void main(String[] args) {
        String array[] = {"red"};
        String color1 = Arrays.stream(array).findAny().get();
        User1 user1 = new User1("login","red");
        List<User1> userEntityList = new ArrayList<>();
        userEntityList.add(user1);
        String login = "login";
        boolean result = userEntityList.stream().allMatch(user -> user.getLogin()==login);
        System.out.println(result);
        String color2 = userEntityList.stream().filter(user->user.getLogin()==login).findAny().orElse(null).getColor();
        System.out.println(color2);
        String color="fiolet";
        userEntityList.stream().filter(user -> user.getLogin()==login).findAny().ifPresent(user -> user.setColor(color));
    }
}
//}peopleList.stream().filter(person -> person.getId() == id).findAny().orElse(null);
class User1{
    public User1(String login,String color){
        this.login=login;
        this.color=color;
    }
    private String login;
    private String color;

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}