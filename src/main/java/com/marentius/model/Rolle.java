package com.marentius.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Role {
    private String roleFirstName;
    private String roleLastName;
    private Person actor;

    // Constructor with parameters
    public Role(String roleFirstName, String roleLastName, Person actor) {
        this.roleFirstName = roleFirstName;
        this.roleLastName = roleLastName;
        this.actor = actor;
    }

    // Default constructor
    public Role() {
    }

    // Getters and Setters
    @JsonIgnore
    public String getRoleFirstName() {
        return roleFirstName;
    }

    public void setRoleFirstName(String roleFirstName) {
        this.roleFirstName = roleFirstName;
    }

    @JsonIgnore
    public String getRoleLastName() {
        return roleLastName;
    }

    public void setRoleLastName(String roleLastName) {
        this.roleLastName = roleLastName;
    }

    @JsonIgnore
    public Person getActor() {
        return actor;
    }

    public void setActor(Person actor) {
        this.actor = actor;
    }

    // toString method
    @Override
    @JsonIgnore
    public String toString() {
        return "Role: " + roleFirstName + " " + roleLastName + " played by " + actor;
    }
}
