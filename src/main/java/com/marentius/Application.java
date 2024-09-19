package com.marentius;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import com.marentius.controller.EpisodeController;
import com.marentius.controller.TvSerieController;
import com.marentius.repository.TvSerieJSONRepository;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        // Start Javalin server
        Javalin app = Javalin.create(config -> {
            config.staticFiles.enableWebjars(); // Aktiverer Webjars for frontend
            // Legger til CORS-innstillinger for å tillate frontend forespørsler
            config.plugins.enableCors(cors -> {
                cors.add(it -> {
                    it.allowHost("http://localhost:5173"); // Tillater forespørsler fra din frontend (Vite/React)
                });
            });
        }).start(1515); // Backend kjører på port 1515

        // Setter opp repository for å håndtere TV-seriedata
        TvSerieJSONRepository tvSerieRepository = new TvSerieJSONRepository("tvshows_10.json");

        // Koble til kontrollerne
        TvSerieController tvSerieController = new TvSerieController(tvSerieRepository);
        EpisodeController episodeController = new EpisodeController(tvSerieRepository);

        // API-endepunkter for TV-serier
        app.get("/api/tvserie", new Handler() {
            @Override
            public void handle(Context context) {
                tvSerieController.getTvSerier(context); // Hent alle TV-serier
            }
        });

        app.get("/api/tvserie/{tvserie-id}", new Handler() {
            @Override
            public void handle(Context context) {
                tvSerieController.getEnTvserie(context); // Hent en spesifikk TV-serie
            }
        });

        // API-endepunkter for episoder
        app.get("/api/tvserie/{tvserie-id}/sesong/{sesong-nr}", new Handler() {
            @Override
            public void handle(Context context) {
                episodeController.getEpisoderiSesong(context); // Hent alle episoder i en sesong
            }
        });

        app.get("/api/tvserie/{tvserie-id}/sesong/{sesong-nr}/episode/{episode-nr}", new Handler() {
            @Override
            public void handle(Context context) {
                episodeController.getEpisode(context); // Hent en spesifikk episode
            }
        });

        // API for å slette en episode
        app.delete("/api/tvserie/{tvserie-id}/sesong/{sesong-nr}/episode/{episode-nr}/delete", new Handler() {
            @Override
            public void handle(Context context) {
                episodeController.slettEpisode(context); // Slett en spesifikk episode
            }
        });

        // API for å opprette en ny episode
        app.post("/api/tvserie/{tvserie-id}/createepisode", new Handler() {
            @Override
            public void handle(Context context) {
                episodeController.opprettEpisode(context); // Opprett en ny episode
            }
        });

        // API for å oppdatere en eksisterende episode
        app.put("/api/tvserie/{tvserie-id}/sesong/{sesong-nr}/episode/{episode-nr}/update", new Handler() {
            @Override
            public void handle(Context context) {
                episodeController.oppdaterEpisode(context); // Oppdater en episode
            }
        });
    }
}
