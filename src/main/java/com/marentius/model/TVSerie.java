package com.marentius.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class TVSerie{
    private String tittel;
    private String beskrivelse;
    private LocalDate utgivelsesdato;
    private ArrayList<Episode> episoder;
    private double gjennomsnittligSpilletid;
    private int antallSesonger;
    private String bildeUrl;


    public TVSerie(String tittel, String beskrivelse, LocalDate utgivelsesdato, ArrayList<Episode> episoder, String bildeUrl) {
        this.tittel = tittel;
        this.beskrivelse = beskrivelse;
        this.utgivelsesdato = utgivelsesdato;
        this.episoder = episoder;
        this.bildeUrl = bildeUrl;
    }

    public TVSerie(){
    }

    public void setGjennomsnittligSpilletid(double gjennomsnittligSpilletid) {
        this.gjennomsnittligSpilletid = gjennomsnittligSpilletid;
    }

    public void setAntallSesonger(int antallSesonger) {
        this.antallSesonger = antallSesonger;
    }

    public void setBildeUrl(String bildeUrl) {
        this.bildeUrl = bildeUrl;
    }

    public String getBildeUrl() {
        return bildeUrl;
    }

    public int getAntallSesonger() {
        return antallSesonger;
    }
    @JsonIgnore
    public double getGjennomsnittligSpilletid() {
        return gjennomsnittligSpilletid;
    }

    public String getTittel() {
        return tittel;
    }

    public void setTittel(String tittel) {
        this.tittel = tittel;
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

    public ArrayList<Episode> getEpisoder() {
        return episoder;
    }

    public void setEpisoder(ArrayList<Episode> episoder) {
        this.episoder = episoder;
    }

    public void leggTilEpisode(Episode episoden) {
        int sesonger = 0;
        for (Episode k: episoder) {
            if (k.getSesongNummer() > sesonger) {
                sesonger++;
            }
        }
        if (episoden.getSesongNummer() <= sesonger + 1) {
            episoder.add(episoden);
            oppdaterGjennomsnittligSpilletid();
        }
        else {
            System.out.println("Denne episoden kan ikke legges til");
        }
        for (Episode s: episoder) {
            if (s.getSesongNummer() > antallSesonger){
                antallSesonger++;
            }

        }
    }

    @Override     @JsonIgnore
    public String toString() {
        return "TVSerie: " + tittel + " (" + utgivelsesdato + ") \n";
    }
    @JsonIgnore
    public ArrayList<Episode> hentEpisoderISesong(int sesong) {
        ArrayList<Episode> e = new ArrayList<>();

        for (Episode ep : episoder) {
            if (ep.getSesongNummer() == sesong) {
                e.add(ep);
            }

        }
        return e;
    }
    @JsonIgnore
    public static int tilfeldigSpilletid(int min, int max) {
        Random tilfedigSpilletid = new Random();
        return tilfedigSpilletid.nextInt(min, max);
    }

    public void leggTilSesongerOgEpisoder(int ses, int epi) {
        for (var i = 1; (i - 1) < ses; i++) {
            for (var k = 1; (k - 1) < epi; k++) {
                boolean episodeExists = false;
                for (Episode e : episoder) {
                    if (e.getSesongNummer() == i && e.getEpisodeNummer() == k) {
                        episodeExists = true;
                        break;
                    }
                }
                if (!episodeExists) {
                    episoder.add(new Episode("Episode " + k, k, i, tilfeldigSpilletid(20,30),""));
                }
            }
        }
        oppdaterGjennomsnittligSpilletid();
    }


    private void oppdaterGjennomsnittligSpilletid() {
        int totalSpilletid = 0;
        int totalEpisoder = episoder.size();
        for (Episode e: episoder) {
            totalSpilletid += e.getSpilletid();
        }
        this.gjennomsnittligSpilletid = (double) totalSpilletid / totalEpisoder;
    }
    @JsonIgnore
    public ArrayList<Rolle> hentRollebesetning() {
        ArrayList<Rolle> rolleliste = new ArrayList<>();

        for (Episode e: episoder) {
            ArrayList<Rolle> roller = e.getRoller();
            rolleliste.addAll(roller);
        }
        return rolleliste;
    }


}