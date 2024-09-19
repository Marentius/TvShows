package com.marentius;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.vue.VueComponent;
import com.marentius.controller.EpisodeController;
import com.marentius.controller.TvSerieController;
import com.marentius.repository.TvSerieJSONRepository;


import java.io.File;
import java.io.IOException;


public class Application {
    public static void main(String[] args) throws IOException {
        Javalin app = Javalin.create(config -> {
            config.staticFiles.enableWebjars();
            config.vue.vueAppName = "app";
        }).start(1515);

        //app.get("/", new VueComponent("hello-world"));
        app.before("/", context -> context.redirect("/tvserie"));

        app.get("/tvserie", new VueComponent("tvserie-overview"));
        app.get("/tvserie/{tvserie-id}/sesong/{sesong-nr}", new VueComponent("tvserie-detail"));
        app.get("/tvserie/{tvserie-id}/sesong/{sesong-nr}/episode/{episode-nr}", new VueComponent("episode-detail"));
        app.get("/tvserie/{tvserie-id}/sesong/{sesong-nr}/episode/{episode-nr}/updateepisode", new VueComponent("episode-update"));
        app.get("/tvserie/{tvserie-id}/createepisode", new VueComponent("episode-create"));

        //Jeg har jobbet i TvSerieJSONRepository for sletting, oppretting og oppdatering av episoder.
        TvSerieJSONRepository tvSerieRepository = new TvSerieJSONRepository("tvshows_10.json");
        //TvSerieCSVRepository tvSerieRepository = new TvSerieCSVRepository(new File("tvshows_10.csv"));
        //TvSerieDataRepository tvSerieRepository = new TvSerieDataRepository();
        TvSerieController tvSerieController = new TvSerieController(tvSerieRepository);
        EpisodeController episodeController = new EpisodeController(tvSerieRepository);

        app.get("api/tvserie", new Handler() {
            @Override
            public void handle(Context context) {
                tvSerieController.getTvSerier(context);
            }
        });

        app.get("api/tvserie/{tvserie-id}", new Handler() {
            @Override
            public void handle(Context context) {
                tvSerieController.getEnTvserie(context);
            }
        });

        app.get("/api/tvserie/{tvserie-id}/sesong/{sesong-nr}", new Handler() {
            @Override
            public void handle(Context context) {
                episodeController.getEpisoderiSesong(context);
            }
        });

        app.get("/api/tvserie/{tvserie-id}/sesong/{sesong-nr}/episode/{episode-nr}", new Handler() {
            @Override
            public void handle(Context context) {
                episodeController.getEpisode(context);
            }
        });

        app.get("/api/tvserie/{tvserie-id}/sesong/{sesong-nr}/episode/{episode-nr}/deleteepisode", new Handler() {
            @Override
            public void handle(Context context) {
                episodeController.slettEpisode(context);
            }
        });

        app.post("/api/tvserie/{tvserie-id}/createepisode", new Handler() {
            @Override
            public void handle(Context context) {
                episodeController.opprettEpisode(context);
            }
        });

        app.post("/api/tvserie/{tvserie-id}/sesong/{sesong-nr}/episode/{episode-nr}/updateepisode", new Handler() {
            @Override
            public void handle(Context context) {
                episodeController.oppdaterEpisode(context);
            }
        });

    }

}
