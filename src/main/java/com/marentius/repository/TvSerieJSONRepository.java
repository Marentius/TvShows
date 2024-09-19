package com.marentius.repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.marentius.model.Episode;
import com.marentius.model.TVSerie;
import com.marentius.thread.WriteToFileThread;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class TvSerieJSONRepository implements TvSerieRepository {

    private ArrayList<TVSerie> tvSerieArrayList = new ArrayList<>();

    public TvSerieJSONRepository(String filename) {
        readFromJsonFile(filename);
        readAndWrite(filename);
    }

    @JsonIgnore
    public ArrayList<TVSerie> readFromJsonFile(String filename) {

        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        try {
            TVSerie[] tvSeriesArray = objectMapper.readValue(new File(filename), TVSerie[].class);

            tvSerieArrayList.addAll(Arrays.asList(tvSeriesArray));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return tvSerieArrayList;
    }

    @Override
    @JsonIgnore
    public ArrayList<TVSerie> getTvSerier() {
        ArrayList<TVSerie> tv = new ArrayList<>();

        for (TVSerie tvSerie : tvSerieArrayList) {
            tv.add(tvSerie);
        }
        return tv;
    }

    @Override
    @JsonIgnore
    public TVSerie getEnTvSerie(String tvSerieNavn) {
        for (TVSerie tvSerie : tvSerieArrayList) {
            if (tvSerie.getTittel().equals(tvSerieNavn)) {
                return tvSerie;
            }
        }
        return null;
    }

    @Override
    @JsonIgnore
    public ArrayList<Episode> getEpisoderiSesong(String tvSerieNavn, int sesongNr) {
        ArrayList<Episode> episodes = new ArrayList<>();
        TVSerie tvSerie = null;

        for (TVSerie serie : tvSerieArrayList) {
            if (serie.getTittel().equals(tvSerieNavn)) {
                tvSerie = serie;
            }
        }

        if (tvSerie != null) {
            for (Episode episode : tvSerie.getEpisoder()) {
                if (episode.getSesongNummer() == sesongNr) {
                    episodes.add(episode);
                }
            }
        }
        return episodes;
    }

    @Override
    @JsonIgnore
    public Episode getEpisode(String tvSerieNavn, int sesongNr, int episodeNr) {
        for (TVSerie serie : tvSerieArrayList) {
            if (serie.getTittel().equals(tvSerieNavn)) {
                for (Episode episode : serie.getEpisoder()) {
                    if (episode.getSesongNummer() == sesongNr && episode.getEpisodeNummer() == episodeNr) {
                        return episode;
                    }
                }
            }
        }
        return null;
    }


    @Override
    public Episode oppdaterEpisode(String tvSerieNavn, String episodeTittel, String episodeBeskrivelse, int episodeNummer, int sesongNummer, int spilletid, LocalDate utgivelsesdato, String bildeUrl) {
        ArrayList<TVSerie> oppdatertListe = getTvSerier();

        for (TVSerie serie : oppdatertListe) {
            if (serie.getTittel().equals(tvSerieNavn)) {

                ArrayList<Episode> episodes = serie.getEpisoder();

                for (Episode episode : episodes) {
                    if (episode.getSesongNummer() == sesongNummer && episode.getEpisodeNummer() == episodeNummer) {

                        episode.setTittel(episodeTittel);
                        episode.setBeskrivelse(episodeBeskrivelse);
                        episode.setEpisodeNummer(episodeNummer);
                        episode.setSesongNummer(sesongNummer);
                        episode.setSpilletid(spilletid);
                        episode.setUtgivelsesdato(utgivelsesdato);
                        episode.setBildeUrl(bildeUrl);

                        serie.setEpisoder(episodes);

                        //writeToFile(oppdatertListe, new File("updated.json"));
                        //Jeg oppretter nye json. filer bare for å vise at filene blir oppdatert uten å endre den eksisterende tvshows_10 filen
                        WriteToFileThread thread = new WriteToFileThread(oppdatertListe, new File("updated.json"));
                        thread.start();


                        return episode;
                    }
                }
            }
        }
        return null;
    }


    @Override
    public Episode slettEpisode(String tvSerieNavn, int sesongNr, int episodeNr) {
        ArrayList<TVSerie> oppdatertListe = getTvSerier();

        for (TVSerie serie : oppdatertListe) {
            if (serie.getTittel().equals(tvSerieNavn)) {

                ArrayList<Episode> episoder = serie.getEpisoder();

                for (Episode episode : episoder) {
                    if (episode.getSesongNummer() == sesongNr && episode.getEpisodeNummer() == episodeNr) {
                        episoder.remove(episode);
                        serie.setEpisoder(episoder);

                        //writeToFile(oppdatertListe, new File("deleted.json"));
                        //Jeg oppretter nye json. filer bare for å vise at filene blir oppdatert uten å endre den eksisterende tvshows_10 filen
                        WriteToFileThread thread = new WriteToFileThread(oppdatertListe, new File("deleted.json"));
                        thread.start();


                        return episode;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Episode opprettEpiode(String tvSerieNavn, String episodeTittel,String beskrivelse, int episodeNr, int sesongNr, int spilletid, LocalDate utgivelsesdato, String bildeURL) {
        ArrayList<TVSerie> oppdatertListe = tvSerieArrayList;

        for (TVSerie serie : oppdatertListe) {

            if (serie.getTittel().equals(tvSerieNavn)) {

                Episode episode = new Episode(episodeTittel, spilletid, beskrivelse, utgivelsesdato, bildeURL, episodeNr, sesongNr);

                serie.leggTilEpisode(episode);

                //writeToFile(oppdatertListe, new File("created.json"));
                //Jeg oppretter nye json. filer bare for å vise at filene blir oppdatert uten å endre den eksisterende tvshows_10 filen
                WriteToFileThread thread = new WriteToFileThread(oppdatertListe, new File("created.json"));
                thread.start();

                return episode;
            }
        }
        return null;
    }


    public void writeToFile(ArrayList<TVSerie> series, File file) {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, series);
        } catch (IOException e) {
            e.printStackTrace();
        }

    } //metodelogikk flyttet til WriteToFileThread

    public void readAndWrite(String filename) {
        ArrayList<TVSerie> backUp = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        try {
            TVSerie[] tvSeriesArray = objectMapper.readValue(new File(filename), TVSerie[].class);

            backUp.addAll(Arrays.asList(tvSeriesArray));

            //objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("backup.json"), backUp);
            //Jeg oppretter nye json. filer bare for å vise at filene blir oppdatert uten å endre den eksisterende tvshows_10 filen
            WriteToFileThread thread = new WriteToFileThread(backUp, new File("backup.json"));
            thread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}