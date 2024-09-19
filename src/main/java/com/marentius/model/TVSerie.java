package com.marentius.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class TVShow {
    private String tittel;
    private String beskrivelse;
    private LocalDate utgivelsesdato;
    private ArrayList<Episode> episoder;
    private double averageRuntime;
    private int numberOfSeasons;
    private String bildeUrl;

    // Constructor
    public TVShow(String tittel, String beskrivelse, LocalDate utgivelsesdato, ArrayList<Episode> episoder, String bildeUrl) {
        this.tittel = tittel;
        this.beskrivelse = beskrivelse;
        this.utgivelsesdato = utgivelsesdato;
        this.episoder = episoder;
        this.bildeUrl = bildeUrl;
    }

    // Default constructor
    public TVShow() {
    }

    // Getters and Setters
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

    public String getBildeUrl() {
        return bildeUrl;
    }

    public void setBildeUrl(String bildeUrl) {
        this.bildeUrl = bildeUrl;
    }

    @JsonIgnore
    public double getAverageRuntime() {
        return averageRuntime;
    }

    public void setAverageRuntime(double averageRuntime) {
        this.averageRuntime = averageRuntime;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    // Add a single episode
    public void addEpisode(Episode episode) {
        int seasons = 0;
        for (Episode e : episoder) {
            if (e.getSesongnummer() > seasons) {
                seasons++;
            }
        }
        if (episode.getSesongnummer() <= seasons + 1) {
            episoder.add(episode);
            updateAverageRuntime();
        } else {
            System.out.println("This episode cannot be added");
        }
        for (Episode e : episoder) {
            if (e.getSesongnummer() > numberOfSeasons) {
                numberOfSeasons++;
            }
        }
    }

    // Update the average runtime based on the episodes
    private void updateAverageRuntime() {
        int totalRuntime = 0;
        int totalEpisodes = episoder.size();
        for (Episode e : episoder) {
            totalRuntime += e.getSpilletid();
        }
        this.averageRuntime = (double) totalRuntime / totalEpisodes;
    }

    // Fetch episodes from a specific season
    @JsonIgnore
    public ArrayList<Episode> getEpisodesInSeason(int season) {
        ArrayList<Episode> seasonEpisodes = new ArrayList<>();
        for (Episode ep : episoder) {
            if (ep.getSesongnummer() == season) {
                seasonEpisodes.add(ep);
            }
        }
        return seasonEpisodes;
    }

    // Add multiple seasons and episodes
    public void addSeasonsAndEpisodes(int seasons, int episodesPerSeason) {
        for (int i = 1; i <= seasons; i++) {
            for (int k = 1; k <= episodesPerSeason; k++) {
                boolean episodeExists = false;
                for (Episode e : episoder) {
                    if (e.getSesongnummer() == i && e.getEpisodenummer() == k) {
                        episodeExists = true;
                        break;
                    }
                }
                if (!episodeExists) {
                    episoder.add(new Episode("Episode " + k, k, i, randomRuntime(20, 30), ""));
                }
            }
        }
        updateAverageRuntime();
    }

    // Generate random runtime
    @JsonIgnore
    public static int randomRuntime(int min, int max) {
        Random random = new Random();
        return random.nextInt(min, max);
    }

    // Fetch the cast of the entire TV show
    @JsonIgnore
    public ArrayList<Role> getCast() {
        ArrayList<Role> castList = new ArrayList<>();
        for (Episode e : episoder) {
            ArrayList<Role> roles = e.getRolleliste();
            castList.addAll(roles);
        }
        return castList;
    }

    // toString method
    @Override
    @JsonIgnore
    public String toString() {
        return "TV Show: " + tittel + " (" + utgivelsesdato + ") \n";
    }
}
