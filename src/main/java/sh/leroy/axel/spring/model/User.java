package sh.leroy.axel.spring.model;

import sh.leroy.axel.spring.dto.UserDto;
import sh.leroy.axel.spring.validation.email.ValidEmail;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
public class User {
    @Id
    private String username;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    private String password;
    @ValidEmail
    @NotNull
    private String email;
    @ElementCollection
    private Set<String> roles;

    public User() {
    }

    public User(UserDto user) {
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.roles = new HashSet<>();
        roles.add("USER");
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return Collections.unmodifiableList(new ArrayList<>(roles));
    }

    public void addRole(String role) {
        roles.add(role);
    }
}
