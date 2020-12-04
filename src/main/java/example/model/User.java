package example.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity()
@Table(name = "people")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 1, max = 30, message = "Name should be between 1 and 30 characters")
    private String login;

    @NotEmpty(message = "Password should not be empty")
    @Size(min = 1, max = 30, message = "Name should be between 1 and 30 characters")
    private String password;

    //    @Email(message = "Email should be valid")
//    @NotEmpty(message = "Email should not be empty")
    private String email;

    private String color;

    @Transient
    String[] array;

    @Transient
    boolean bol;

    public boolean isBol() {
        return bol;
    }

    public void setBol(boolean bol) {
        this.bol = bol;
    }

    @Transient
    List<User> users;

    private String role;

    public User(int id, String login, String password, String email, String color, String role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
        this.color = color;
        this.role = role;

    }

    public String[] getArray() {
        return array;
    }

    public void setArray(String[] array) {
        this.array = array;
    }

    public User() {
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Integer getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getColor() {
        return color;
    }

    public String getRole() {
        return role;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
