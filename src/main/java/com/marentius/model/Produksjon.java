package com.marentius.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.ArrayList;

public abstract class Produksjon {
    private String beskrivelse;
    private LocalDate utgivelsesdato;
    private String tittel;
    private int spilletid;
    private Person regissor;
    private ArrayList<Rolle> roller = new ArrayList<>();
    private String bildeUrl;


    public Produksjon(String tittel, int spilletid, String bildeUrl) {
        this.tittel = tittel;
        this.spilletid = spilletid;
        this.bildeUrl = bildeUrl;
    }

    public Produksjon(String tittel, int spilletid, String beskrivelse, LocalDate utgivelsesdato, String bildeUrl) {
        this.tittel = tittel;
        this.spilletid = spilletid;
        this.beskrivelse = beskrivelse;
        this.utgivelsesdato = utgivelsesdato;
        this.bildeUrl = bildeUrl;
    }

    public Produksjon() {

    }

    public String getBildeUrl() {
        return bildeUrl;
    }

    public String getTittel() {
        return tittel;
    }

    public void setTittel(String tittel) {
        this.tittel = tittel;
    }

    public int getSpilletid() {
        return spilletid;
    }

    public void setSpilletid(int spilletid) {
        this.spilletid = spilletid;
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

    public Person getRegissor() {
        return regissor;
    }

    public void setRegissor(Person regissor) {
        this.regissor = regissor;
    }

    @JsonProperty("rolleListe")
    public ArrayList<Rolle> getRoller() {
        return roller;
    }

    public void setRoller(ArrayList<Rolle> roller) {
        this.roller = roller;
    }

    public void setBildeUrl(String bildeUrl) {
        this.bildeUrl = bildeUrl;
    }

    public void leggTilEnRolle(Rolle enRolle) {
        roller.add(enRolle);
    }

    public void leggTilMangeRoller(ArrayList<Rolle> flereRoller) {
        roller.addAll(flereRoller);
    }

    @Override     @JsonIgnore
    public String toString() {
        return tittel + " er ressigert av " + regissor;
    }
}