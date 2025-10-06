package com.baontin.jobportal.entity;

import jakarta.persistence.*;

@Entity
public class JobLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private String city;
    private String country;
    private String state;

    public JobLocation() {}

    public JobLocation(int id, String city, String country, String state) {
        this.id = id;
        this.city = city;
        this.country = country;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "JobLocation{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
