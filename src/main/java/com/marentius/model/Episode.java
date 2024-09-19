package com.marentius.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

public class Episode extends Produksjon implements Comparable<Episode>{
    private int episodeNummer;
    private int sesongNummer;


    public Episode(String tittel, int episodeNummer, int sesongNummer, int spilletid, LocalDate utgivelsesdato, String beskrivelse) {
        super(tittel,spilletid, beskrivelse);
        this.episodeNummer = episodeNummer;
        this.sesongNummer = sesongNummer;
    }
    public Episode(String tittel, int episodeNummer, int sesongNummer, int spilletid, String bildeUrl) {
        super(tittel,spilletid, bildeUrl);
        this.episodeNummer = episodeNummer;
        this.sesongNummer = sesongNummer;
    }

    public Episode(String tittel, int spilletid, String beskrivelse, LocalDate utgivelsesdato, String bildeUrl, int episodeNummer, int sesongNummer) {
        super(tittel, spilletid, beskrivelse, utgivelsesdato, bildeUrl);
        this.episodeNummer = episodeNummer;
        this.sesongNummer = sesongNummer;
    }

    public Episode(String tittel, int spilletid, String beskrivelse, LocalDate utgivelsesdato, String bildeUrl, int episodeNummer, int sesongNummer, Person person) {
        super(tittel, spilletid, beskrivelse, utgivelsesdato, bildeUrl);
        this.episodeNummer = episodeNummer;
        this.sesongNummer = sesongNummer;
    }


    public Episode(){}

    @Override     @JsonIgnore
    public int compareTo(@NotNull Episode annenEpisode) {
        return this.episodeNummer - annenEpisode.getEpisodeNummer();
    }

    public int getEpisodeNummer() {
        return episodeNummer;
    }

    public void setEpisodeNummer(int episodeNummer) {
        this.episodeNummer = episodeNummer;
    }


    public int getSesongNummer() {
        return sesongNummer;
    }

    public void setSesongNummer(int sesongNummer) {
        this.sesongNummer = sesongNummer;
    }


    @Override     @JsonIgnore
    public String toString() {
        return "Episode: " + super.getTittel() + " (Sesong " + sesongNummer + ", episode " + episodeNummer + ")";
    }

}
