package com.marentius.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Rolle{
    private String rolleFornavn, rolleEtternavn;
    private Person skuespiller;

    public Rolle(String rolleFornavn, String rolleEtternavn, Person skuespiller) {
        this.rolleFornavn = rolleFornavn;
        this.rolleEtternavn = rolleEtternavn;
        this.skuespiller = skuespiller;
    }

    public Rolle() {
    }
    @JsonIgnore
    public String getRolleFornavn() {
        return rolleFornavn;
    }

    public void setRolleFornavn(String rolleFornavn) {
        this.rolleFornavn = rolleFornavn;
    }
    @JsonIgnore
    public String getRolleEtternavn() {
        return rolleEtternavn;
    }

    public void setRolleEtternavn(String rolleEtternavn) {
        this.rolleEtternavn = rolleEtternavn;
    }
    @JsonIgnore
    public Person getSkuespiller() {
        return skuespiller;
    }

    public void setSkuespiller(Person skuespiller) {
        this.skuespiller = skuespiller;
    }

    @Override     @JsonIgnore
    public String toString() {
        return "Rolle: " + rolleFornavn + " " + rolleEtternavn + " " + skuespiller;
    }

}