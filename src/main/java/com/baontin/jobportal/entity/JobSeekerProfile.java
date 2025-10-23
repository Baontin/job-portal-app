package com.baontin.jobportal.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "job_seeker_profile")
public class JobSeekerProfile {

    @Id
    private Integer userAccountId;

    @OneToOne
    @JoinColumn(name = "user_account_id")
    @MapsId // Use this entity’s primary key as a foreign key to another entity.
    private Users userId;

    private String firstName;
    private String lastName;
    private String profilePhoto;
    private String country;
    private String state;
    private String city;
    private String employmentType;
    private String workAuthorization;
    private String resume;

    @OneToMany(targetEntity = Skills.class, cascade = CascadeType.ALL,
                mappedBy = "jobSeekerProfile")
    private List<Skills> skills;

    public JobSeekerProfile() {}

    public JobSeekerProfile(Users users) {
        this.userId = users;
    }

    public JobSeekerProfile(Integer userAccountId, Users userId, String firstName, String lastName,
                String profilePhoto, String country, String state, String city, String employmentType,
                String workAuthorization, String resume, List<Skills> skills) {
        this.userAccountId = userAccountId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profilePhoto = profilePhoto;
        this.country = country;
        this.state = state;
        this.city = city;
        this.employmentType = employmentType;
        this.workAuthorization = workAuthorization;
        this.resume = resume;
        this.skills = skills;
    }

    public Integer getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(Integer userAccountId) {
        this.userAccountId = userAccountId;
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

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public String getWorkAuthorization() {
        return workAuthorization;
    }

    public void setWorkAuthorization(String workAuthorization) {
        this.workAuthorization = workAuthorization;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public List<Skills> getSkills() {
        return skills;
    }

    public void setSkills(List<Skills> skills) {
        this.skills = skills;
    }

    @Transient
    public String getPhotosImagePath() {
        if (profilePhoto == null || userAccountId == null) return null;
        return "/photos/candidate/" + userAccountId + "/" + profilePhoto;
    }

    @Override
    public String toString() {
        return "JobSeekerProfile{" +
                "userAccountId=" + userAccountId +
                ", userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", profilePhoto='" + profilePhoto + '\'' +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", employmentType='" + employmentType + '\'' +
                ", workAuthorization='" + workAuthorization + '\'' +
                ", resume='" + resume + '\'' +
                '}';
    }
}
