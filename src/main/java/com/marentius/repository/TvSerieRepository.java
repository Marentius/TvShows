package com.marentius.repository;

import com.marentius.model.Episode;
import com.marentius.model.TVShow;

import java.time.LocalDate;
import java.util.ArrayList;

public interface TvShowRepository {

    // Get all TV shows
    ArrayList<TVShow> getTvShows();

    // Get a specific TV show by name
    TVShow getTvShow(String tvShowName);

    // Get all episodes of a specific season of a TV show
    ArrayList<Episode> getEpisodesInSeason(String tvShowName, int seasonNumber);

    // Get a specific episode by its number and season in a TV show
    Episode getEpisode(String tvShowName, int seasonNumber, int episodeNumber);

    // Update an episode in a TV show
    Episode updateEpisode(String tvShowName, String episodeTitle, String episodeDescription, int episodeNumber, int seasonNumber, int runtime, LocalDate releaseDate, String imageUrl);

    // Delete an episode in a TV show
    Episode deleteEpisode(String tvShowName, int seasonNumber, int episodeNumber);

    // Create a new episode in a TV show
    Episode createEpisode(String tvShowName, String episodeTitle, String description, int episodeNumber, int seasonNumber, int runtime, LocalDate releaseDate, String imageUrl);
}
