package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;
import fr.lernejo.navy_battle.Handler.CallHandler;
import fr.lernejo.navy_battle.Handler.FireHandler;
import fr.lernejo.navy_battle.Handler.PostHandler;
import fr.lernejo.navy_battle.Partie.Jeu;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Serveur {

    public HttpServer launch(int port, Jeu jeu) throws IOException {
        final HttpServer httpServer = HttpServer.create(new InetSocketAddress(port), 0);
        httpServer.createContext("/ping", new CallHandler());
        httpServer.createContext("/api/game/start", new PostHandler(jeu));
        httpServer.createContext("/api/game/fire", new FireHandler(jeu));
        httpServer.setExecutor(Executors.newFixedThreadPool(1));

        return httpServer;
    }
}
