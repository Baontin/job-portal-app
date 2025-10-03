package com.baontin.jobportal.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "recruiter_profile")
public class RecruiterProfile {

    @Id
    private int user_account_id;

    @OneToOne
    @JoinColumn(name = "user_account_id")
    @MapsId
    private Users userId;

    private String firstName;
    private String lastName;
    private String company;
    private String country;
    private String city;
    private String state;

    @Column(nullable = true, length = 64)
    private String profilePhoto;

    public RecruiterProfile() {}

    public RecruiterProfile(Users users) {
        this.userId = users;
    }

    public RecruiterProfile(int user_account_id, Users userId, String firstName, String lastName,
            String company, String country, String city, String state, String profilePhoto) {
        this.user_account_id = user_account_id;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = company;
        this.country = country;
        this.city = city;
        this.state = state;
        this.profilePhoto = profilePhoto;
    }

    public int getUserAccountId() {
        return user_account_id;
    }

    public void setUserAccountId(int user_account_id) {
        this.user_account_id = user_account_id;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }



    @Override
    public String toString() {
        return "RecruiterProfile{" +
                "user_account_id=" + user_account_id +
                ", userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", company='" + company + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", profilePhoto='" + profilePhoto + '\'' +
                '}';
    }
}
