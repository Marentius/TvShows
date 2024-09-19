package com.marentius.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;

public class Film extends Produksjon{

    public Film(String tittel, int spilletid, String beskrivelse, LocalDate utgivelsesdato) {
        super(tittel,spilletid, beskrivelse);
    }

    public Film(String tittel, int spilletid, String bildeUrl) {
        super(tittel,spilletid, bildeUrl);
    }

    public Film() {
    }

    @Override    @JsonIgnore
    public String toString() {
        return "Tittel: " +  super.getTittel() + " Utgivelsesdato: " + super.getUtgivelsesdato();
    }

}