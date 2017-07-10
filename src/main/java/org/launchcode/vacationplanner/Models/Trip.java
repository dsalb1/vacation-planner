package org.launchcode.vacationplanner.Models;




import javax.persistence.*;
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

    private String name;

    private String description;

    private Integer lengthNight;

    @OneToMany
    @JoinColumn(name = "trip_id")
    private List<PointOfInterest> interests = new ArrayList<>();

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

    public List<PointOfInterest> getInterests() {
        return interests;
    }

    public void setInterests(List<PointOfInterest> interests) {
        this.interests = interests;
    }

    public void addInterests(PointOfInterest anInterest) {
        this.interests.add(anInterest);
    }

}


