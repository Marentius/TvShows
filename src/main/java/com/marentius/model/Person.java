package com.marentius.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class Person {
    private String fulltNavn;
    private String fornavn;
    private String etterNavn;
    private int alder;
    private String nasjonalitet;
    private LocalDate fodselsDato;

    public Person(String fornavn, String etterNavn, LocalDate fodselsDato) {
        this.fornavn = fornavn;
        this.etterNavn = etterNavn;
        this.fodselsDato = fodselsDato;
    }

    public Person(String fulltNavn, String etterNavn, int alder, String nasjonalitet) {
        this.fulltNavn = fulltNavn;
        this.etterNavn = etterNavn;
        this.alder = alder;
        this.nasjonalitet = nasjonalitet;
    }

    public Person(String fulltNavn, String etterNavn) {
        this.fulltNavn = fulltNavn;
        this.etterNavn = etterNavn;
    }

    public Person(String fulltNavn) {
        this.fulltNavn = fulltNavn;
    }

    public Person(){
    }

    @JsonIgnore
    public String getFulltNavn() {
        return fulltNavn;
    }

    public void setFulltNavn(String fulltNavn) {
        this.fulltNavn = fulltNavn;
    }

    @JsonProperty("etternavn")
    public String getEtterNavn() {
        return etterNavn;
    }

    public void setEtterNavn(String etterNavn) {
        this.etterNavn = etterNavn;
    }

    @JsonIgnore
    public int getAlder() {
        return alder;
    }

    public void setAlder(int alder) {
        this.alder = alder;
    }

    @JsonIgnore
    public String getNasjonalitet() {
        return nasjonalitet;
    }

    public void setNasjonalitet(String nasjonalitet) {
        this.nasjonalitet = nasjonalitet;
    }

    public String navn() {
        return getFulltNavn() + " " + getEtterNavn();
    }

    public LocalDate getFodselsDato() {
        return fodselsDato;
    }

    public void setFodselsDato(LocalDate fodselsDato) {
        this.fodselsDato = fodselsDato;
    }

    public String getFornavn() {
        return fornavn;
    }

    public void setFornavn(String fornavn) {
        this.fornavn = fornavn;
    }


    @Override     @JsonIgnore
    public String toString() {
        return " - Skuespiller: " + fulltNavn + " " + etterNavn;
    }
}