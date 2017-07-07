package org.launchcode.vacationplanner.Models;

import javax.persistence.Entity;
import javax.persistence.*;

/**
 * Created by Dan on 7/5/2017.
 */

@Entity
public class PointOfInterest {

    @Id
    @GeneratedValue
    private int id;

    private String name;

    private String description;

    private Double cost;

    private String address;

    private String phone;

    @ManyToOne
    private Trip trip;

    public PointOfInterest() {}

    public PointOfInterest(String name) {
        this.name = name;
    }

    public PointOfInterest(String name, String description, Double cost, String address, String phone, Trip trip) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.phone = phone;
        this.trip = trip;
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

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }
}
