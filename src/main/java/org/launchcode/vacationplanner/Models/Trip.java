package org.launchcode.vacationplanner.Models;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Dan on 7/1/2017.
 */

@Entity
public class Trip {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=2, max=50)
    private String name;

    private String description;

    private Integer lengthNight;

    private String location;

    @OneToMany
    @JoinColumn(name = "trip_id")
    private List<PointOfInterest> interests = new ArrayList<>();

    @ManyToOne
    private User user;

    public Trip(){}

    public Trip(String name, String description) {
        this.name = name;
        this.description = description;

    }

    public Trip(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLengthNight() {
        return lengthNight;
    }

    public void setLengthNight(Integer lengthNight) {
        this.lengthNight = lengthNight;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    //adds up cost of every point of interest to determine budget
    public String getBudget() {
        Double sum = 0.00;
        for (PointOfInterest point : this.getInterests()) {
            sum += point.getCost();
        }
        return "$" + sum;
    }

    public List<PointOfInterest> getInterests() {
        return interests;
    }

    public void setInterests(List<PointOfInterest> interests) {
        this.interests = interests;
    }

    public void addInterests(PointOfInterest anInterest) {
        this.interests.add(anInterest);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}


