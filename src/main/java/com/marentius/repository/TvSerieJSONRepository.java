package com.marentius.repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.marentius.model.Episode;
import com.marentius.model.TVShow;
import com.marentius.thread.WriteToFileThread;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class TvShowJSONRepository implements TvShowRepository {

    private ArrayList<TVShow> tvShowList = new ArrayList<>();

    public TvShowJSONRepository(String filename) {
        readFromJsonFile(filename);
        readAndWrite(filename);
    }

    @JsonIgnore
    public ArrayList<TVShow> readFromJsonFile(String filename) {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        try {
            TVShow[] tvShowArray = objectMapper.readValue(new File(filename), TVShow[].class);
            tvShowList.addAll(Arrays.asList(tvShowArray));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tvShowList;
    }

    @Override
    @JsonIgnore
    public ArrayList<TVShow> getTvShows() {
        return new ArrayList<>(tvShowList);
    }

    @Override
    @JsonIgnore
    public TVShow getTvShow(String tvShowName) {
        for (TVShow tvShow : tvShowList) {
            if (tvShow.getTittel().equals(tvShowName)) {
                return tvShow;
            }
        }
        return null;
    }

    @Override
    @JsonIgnore
    public ArrayList<Episode> getEpisodesInSeason(String tvShowName, int seasonNumber) {
        ArrayList<Episode> episodes = new ArrayList<>();
        TVShow tvShow = null;

        for (TVShow show : tvShowList) {
            if (show.getTittel().equals(tvShowName)) {
                tvShow = show;
                break;
            }
        }

        if (tvShow != null) {
            for (Episode episode : tvShow.getEpisoder()) {
                if (episode.getSesongnummer() == seasonNumber) {
                    episodes.add(episode);
                }
            }
        }
        return episodes;
    }

    @Override
    @JsonIgnore
    public Episode getEpisode(String tvShowName, int seasonNumber, int episodeNumber) {
        for (TVShow show : tvShowList) {
            if (show.getTittel().equals(tvShowName)) {
                for (Episode episode : show.getEpisoder()) {
                    if (episode.getSesongnummer() == seasonNumber && episode.getEpisodenummer() == episodeNumber) {
                        return episode;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Episode updateEpisode(String tvShowName, String episodeTitle, String episodeDescription, int episodeNumber, int seasonNumber, int runtime, LocalDate releaseDate, String imageUrl) {
        ArrayList<TVShow> updatedList = getTvShows();

        for (TVShow show : updatedList) {
            if (show.getTittel().equals(tvShowName)) {
                ArrayList<Episode> episodes = show.getEpisoder();

                for (Episode episode : episodes) {
                    if (episode.getSesongnummer() == seasonNumber && episode.getEpisodenummer() == episodeNumber) {
                        episode.setTitle(episodeTitle);
                        episode.setBeskrivelse(episodeDescription);
                        episode.setEpisodenummer(episodeNumber);
                        episode.setSesongnummer(seasonNumber);
                        episode.setSpilletid(runtime);
                        episode.setUtgivelsesdato(releaseDate);
                        episode.setBildeUrl(imageUrl);

                        show.setEpisoder(episodes);

                        WriteToFileThread thread = new WriteToFileThread(updatedList, new File("updated.json"));
                        thread.start();

                        return episode;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Episode deleteEpisode(String tvShowName, int seasonNumber, int episodeNumber) {
        ArrayList<TVShow> updatedList = getTvShows();

        for (TVShow show : updatedList) {
            if (show.getTittel().equals(tvShowName)) {
                ArrayList<Episode> episodes = show.getEpisoder();

                for (Episode episode : episodes) {
                    if (episode.getSesongnummer() == seasonNumber && episode.getEpisodenummer() == episodeNumber) {
                        episodes.remove(episode);
                        show.setEpisoder(episodes);

                        WriteToFileThread thread = new WriteToFileThread(updatedList, new File("deleted.json"));
                        thread.start();

                        return episode;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Episode createEpisode(String tvShowName, String episodeTitle, String description, int episodeNumber, int seasonNumber, int runtime, LocalDate releaseDate, String imageUrl) {
        ArrayList<TVShow> updatedList = tvShowList;

        for (TVShow show : updatedList) {
            if (show.getTittel().equals(tvShowName)) {
                Episode episode = new Episode(episodeTitle, runtime, description, releaseDate, imageUrl, episodeNumber, seasonNumber);

                show.addEpisode(episode);

                WriteToFileThread thread = new WriteToFileThread(updatedList, new File("created.json"));
                thread.start();

                return episode;
            }
        }
        return null;
    }

    public void writeToFile(ArrayList<TVShow> shows, File file) {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, shows);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readAndWrite(String filename) {
        ArrayList<TVShow> backup = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        try {
            TVShow[] tvShowArray = objectMapper.readValue(new File(filename), TVShow[].class);

            backup.addAll(Arrays.asList(tvShowArray));

            WriteToFileThread thread = new WriteToFileThread(backup, new File("backup.json"));
            thread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
