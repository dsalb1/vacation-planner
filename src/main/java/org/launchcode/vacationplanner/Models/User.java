package org.launchcode.vacationplanner.Models;


import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Dan on 7/5/2017.
 */

@Entity
/*
@Table(
        name="User",
        uniqueConstraints=
        @UniqueConstraint(columnNames={"id", "username", "email"})
)
*/
public class User {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=3, max=15)
    /*
    @Column(name="username", unique=true)
     */
    private String username;

    @NotNull
    private String password;

    @Email
    /*
    @Column(name="username", unique=true)
     */
    private String email;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Trip> interests = new ArrayList<>();

    public User() {}

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public List<Trip> getInterests() {
        return interests;
    }

    public void setInterests(List<Trip> interests) {
        this.interests = interests;
    }
}
