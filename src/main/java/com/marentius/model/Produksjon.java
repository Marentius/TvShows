package com.marentius.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.ArrayList;

public abstract class Production {
    private String title;
    private String beskrivelse;
    private LocalDate utgivelsesdato;
    private int spilletid;
    private Person regissor;
    private ArrayList<Role> rolleliste = new ArrayList<>();
    private String bildeUrl;

    // Constructors
    public Production(String title, int duration, String bildeUrl) {
        this.title = title;
        this.spilletid = duration;
        this.bildeUrl = bildeUrl;
    }

    public Production(String title, int duration, String description, LocalDate releaseDate, String bildeUrl) {
        this.title = title;
        this.spilletid = duration;
        this.beskrivelse = description;
        this.utgivelsesdato = releaseDate;
        this.bildeUrl = bildeUrl;
    }

    public Production() {
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBeskrivelse() {
        return beskrivelse;
    }

    public void setBeskrivelse(String beskrivelse) {
        this.beskrivelse = beskrivelse;
    }

    public LocalDate getUtgivelsesdato() {
        return utgivelsesdato;
    }

    public void setUtgivelsesdato(LocalDate utgivelsesdato) {
        this.utgivelsesdato = utgivelsesdato;
    }

    public int getSpilletid() {
        return spilletid;
    }

    public void setSpilletid(int spilletid) {
        this.spilletid = spilletid;
    }

    public Person getRegissor() {
        return regissor;
    }

    public void setRegissor(Person regissor) {
        this.regissor = regissor;
    }

    @JsonProperty("roleList")
    public ArrayList<Role> getRolleliste() {
        return rolleliste;
    }

    public void setRolleliste(ArrayList<Role> rolleliste) {
        this.rolleliste = rolleliste;
    }

    public String getBildeUrl() {
        return bildeUrl;
    }

    public void setBildeUrl(String bildeUrl) {
        this.bildeUrl = bildeUrl;
    }

    // Add a single role
    public void addRole(Role role) {
        rolleliste.add(role);
    }

    // Add multiple roles
    public void addRoles(ArrayList<Role> multipleRoles) {
        rolleliste.addAll(multipleRoles);
    }

    @Override
    @JsonIgnore
    public String toString() {
        return title + " is directed by " + regissor;
    }
}
