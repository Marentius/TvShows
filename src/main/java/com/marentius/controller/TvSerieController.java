package com.marentius.controller;

import io.javalin.http.Context;
import com.marentius.model.TVShow;
import com.marentius.repository.TvSerieRepository;

import java.util.ArrayList;

public class TvShowController {
    private final TvSerieRepository tvSerieRepository;

    public TvShowController(TvSerieRepository tvSerieRepository) {
        this.tvSerieRepository = tvSerieRepository;
    }

    // Get all TV shows
    public void getTvShows(Context context) {
        ArrayList<TVShow> tvShows = tvSerieRepository.getTvShows();
        context.json(tvShows);
    }

    // Get a single TV show by its name
    public void getTvShow(Context context) {
        String tvShowName = context.pathParam("tvshow-id");
        TVShow tvShow = tvSerieRepository.getTvShow(tvShowName);

        if (tvShow != null) {
            context.json(tvShow);
        } else {
            context.status(404).result("TV show not found");
        }
    }
}
