package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;
import fr.lernejo.navy_battle.Partie.Jeu;
import fr.lernejo.navy_battle.Partie.Joueur;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PostHandlerTest {
    private final Joueur joueur = new Joueur();
    private final Jeu jeu = new Jeu(this.joueur);
    private final HttpClient httpClient = HttpClient.newHttpClient();

    @Test
    void send_202() throws IOException, InterruptedException {
        HttpServer server = new Serveur().launch(9876, this.jeu);
        server.start();
        HttpRequest postRequest = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:9876/api/game/start"))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString("{\"id\":\"1\", \"url\":\"http://localhost:9876\", \"message\":\"POST REQUEST\"}"))
            .build();

        Assertions.assertThat(this.httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString()).statusCode())
            .as(" /api/game/start 202")
            .isEqualTo(202);
        server.stop(1);
    }

    @Test
    void send_400() throws IOException, InterruptedException {
        HttpServer server = new Serveur().launch(9876, this.jeu);
        server.start();
        HttpRequest postRequest = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:9876/api/game/start"))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString("{}"))
            .build();

        Assertions.assertThat(this.httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString()).statusCode())
            .as(" /api/game/start 400")
            .isEqualTo(400);
        server.stop(1);
    }
}
