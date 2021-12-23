package fr.lernejo.navy_battle.Handler;

import com.sun.net.httpserver.HttpServer;
import fr.lernejo.navy_battle.Partie.Jeu;
import fr.lernejo.navy_battle.Partie.Joueur;
import fr.lernejo.navy_battle.Serveur;
import fr.lernejo.navy_battle.Utils.PostRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.HttpClient;

class PostHandlerTest {
    private final Joueur joueur = new Joueur();
    private final Jeu jeu = new Jeu(this.joueur);
    private final HttpClient client = HttpClient.newHttpClient();

    @Test
    void sendPostRequest() throws IOException, InterruptedException {
        HttpServer server = new Serveur().launch(9876, this.jeu);
        server.start();
        PostRequest request = new PostRequest(9876);
        String response = request.sendPostRequest("http://localhost:9876");
        Assertions.assertThat(response)
            .as("Reponse ")
            .isEqualTo("{\"id\":\"0\", \"url\":\"http://localhost:9876\", \"message\":\"Response\"}");
        server.stop(1);

    }
}
