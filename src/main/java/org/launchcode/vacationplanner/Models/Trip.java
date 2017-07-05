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

    public List<PointOfInterest> getInterests() {
        return interests;
    }

    public void addInterests(List<PointOfInterest> interests, PointOfInterest anInterest) {
        this.interests.add(anInterest);
    }
}


