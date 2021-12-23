package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;
import fr.lernejo.navy_battle.Partie.Jeu;
import fr.lernejo.navy_battle.Partie.Joueur;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ServeurTest {
    private final int port = 9876;
    private final Joueur joueur = new Joueur();
    private final Jeu game = new Jeu(this.joueur);

    @Test
    void launch() throws IOException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        HttpServer httpServer = new Serveur().launch(this.port, this.game);
        httpServer.start();

        String stringExpected = "HTTP server started on port" + this.port + "...\n";
        Assertions.assertThat(outContent.toString()).as("server message starting ").isEqualTo(stringExpected);
        httpServer.stop(1);
    }
}
