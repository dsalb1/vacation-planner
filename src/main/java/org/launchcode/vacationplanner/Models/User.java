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
    @Column(name="ID")
    private int id;

    @NotNull
    @Size(min=3, max=15)
    @Column(name="USERNAME")
    private String username;

    @NotNull
    @Column(name="PASSWORD")
    private String password;

    @Email
    @Column(name="EMAIL")
    private String email;

    @Size(min=3)
    @Column(name="LOCATION")
    private String location;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Trip> trips = new ArrayList<>();

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }
}
