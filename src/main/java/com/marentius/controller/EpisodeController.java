package com.marentius.controller;

import io.javalin.http.Context;
import com.marentius.model.Episode;
import com.marentius.repository.TvSerieRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

public class EpisodeController {
    private TvSerieRepository tvSerieRepository;

    public EpisodeController(TvSerieRepository tvSerieRepository) {
        this.tvSerieRepository = tvSerieRepository;
    }

    public void getEpisoderiSesong(Context context) {
        String tvSerieNavn = context.pathParam("tvserie-id");
        int sesongNummer = Integer.parseInt(context.pathParam("sesong-nr"));

        ArrayList<Episode> episodes = tvSerieRepository.getEpisoderiSesong(tvSerieNavn, sesongNummer);

        String sortering = context.queryParam("sortering");
        if ("episodenr".equals(sortering)) {
            sorterEpisoderEtterEpisodeNr(episodes);
        } else if ("tittel".equals(sortering)) {
            sorterEpisoderEtterTittel(episodes);
        } else if ("spilletid".equals(sortering)) {
            sorterEpisoderEtterSpilletid(episodes);
        }

        context.json(episodes);
    }


    public void getEpisode(Context context) {
        String tvSerieNavn = context.pathParam("tvserie-id");
        int sesongNummer = Integer.parseInt(context.pathParam("sesong-nr"));
        int episodeNummer = Integer.parseInt(context.pathParam("episode-nr"));

        Episode episode = tvSerieRepository.getEpisode(tvSerieNavn, sesongNummer, episodeNummer);

        context.json(episode);
    }

    public void slettEpisode(Context context) {
        String tvSerieNavn = context.pathParam("tvserie-id");
        int sesongNummer = Integer.parseInt(context.pathParam("sesong-nr"));
        int episodeNummer = Integer.parseInt(context.pathParam("episode-nr"));

        Episode episode = tvSerieRepository.slettEpisode(tvSerieNavn, sesongNummer, episodeNummer);

        context.redirect("/tvserie/"+tvSerieNavn+"/sesong/"+sesongNummer);
    }


    public void opprettEpisode(Context context) {
        String[] parametere = hentEpisodeParametere(context);
        tvSerieRepository.opprettEpiode(parametere[0], parametere[1], parametere[2], Integer.parseInt(parametere[3]), Integer.parseInt(parametere[4]), Integer.parseInt(parametere[5]), LocalDate.parse(parametere[6]), parametere[7]);
        context.redirect("/tvserie/" + parametere[0] + "/sesong/" + parametere[4]);
    }

    public void oppdaterEpisode(Context context) {
        String[] parametere = hentEpisodeParametere(context);
        tvSerieRepository.oppdaterEpisode(parametere[0], parametere[1], parametere[2], Integer.parseInt(parametere[3]), Integer.parseInt(parametere[4]), Integer.parseInt(parametere[5]), LocalDate.parse(parametere[6]), parametere[7]);
        context.redirect("/tvserie/" + parametere[0] + "/sesong/" + parametere[4] + "/episode/" + parametere[3]);
    }


    private String[] hentEpisodeParametere(Context context) {
        String tvSerieNavn = context.pathParam("tvserie-id");
        String episodeTittel = context.formParam("tittel");
        String episodeBeskrivelse = context.formParam("beskrivelse");
        String episodeNummer = String.valueOf(context.formParam("episodeNummer"));
        String sesongNummer = String.valueOf(context.formParam("sesongNummer"));
        String spilletid = String.valueOf(context.formParam("spilletid"));
        String utgivelsesdato = String.valueOf(context.formParam("utgivelsesdato"));
        String bildeUrl = context.formParam("bildeUrl");

        return new String[]{tvSerieNavn, episodeTittel, episodeBeskrivelse, episodeNummer, sesongNummer, spilletid, utgivelsesdato, bildeUrl};
    }

    private void sorterEpisoderEtterEpisodeNr(ArrayList<Episode> episodes) {
        episodes.sort(Comparator.comparingInt(Episode::getEpisodeNummer));
    }

    private void sorterEpisoderEtterTittel(ArrayList<Episode> episodes) {
        episodes.sort(Comparator.comparing(Episode::getTittel));
    }

    private void sorterEpisoderEtterSpilletid(ArrayList<Episode> episodes) {
        episodes.sort(Comparator.comparing(Episode::getSpilletid));
    }


}