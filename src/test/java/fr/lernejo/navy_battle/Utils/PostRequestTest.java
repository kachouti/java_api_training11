package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;
import fr.lernejo.navy_battle.Partie.Jeu;
import fr.lernejo.navy_battle.Partie.Joueur;
import fr.lernejo.navy_battle.Utils.PostRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.HttpClient;

public class PostRequestTest {
    private final Joueur joueur = new Joueur();
    private final Jeu jeu = new Jeu(this.joueur);

    @Test
    void sendPostRequest() throws IOException, InterruptedException {
        HttpServer serv= new Serveur().launch(9876, this.jeu);
        serv.start();
        PostRequest request = new PostRequest(9876);
        String response = request.sendPostRequest("http://localhost:9876");
        Assertions.assertThat(response)
            .as("Post Request return response")
            .isEqualTo("{\"id\":\"0\", \"url\":\"http://localhost:9876\", \"message\":\"server message starting\"}");
        serv.stop(1);

    }
}
