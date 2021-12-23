package fr.lernejo.navy_battle.Handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import fr.lernejo.navy_battle.Partie.Jeu;
import fr.lernejo.navy_battle.Utils.Validation;
import org.json.JSONObject;
import java.net.*;
import java.net.http.HttpClient;
import java.io.*;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FireHandler implements HttpHandler {
    private final Jeu jeu;

    public final String schema = "{\"$schema\":\"http://json-schema.org/schema#\",\"type\":\"object\"," +
        "\"properties\":{\"consequence\":{\"type\":\"string\",\"enum\":[\"miss\",\"hit\",\"sunk\"]}," +
        "\"shipLeft\":{\"type\":\"boolean\"}},\"required\":[\"consequence\",\"shipLeft\"]}";

    public String sendFireRequest(String adversaryURL, String cell) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(adversaryURL + "/api/game/fire?cell=" + cell))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .build();
        HttpResponse<String> httpResponse = httpClient.send(req, HttpResponse.BodyHandlers.ofString());
        return httpResponse.body();
    }



    public void app(String res, String cell) throws IOException {
        JSONObject object = new JSONObject(res);
        String cons = object.getString("consequence");
        boolean shipLeft = object.getBoolean("shipLeft");
        this.jeu.bateauMod(cons, cell);
        this.jeu.bateauD();
        if(!shipLeft){System.out.println("Tu as gangé!!");
            System.exit(0);}
    }
    public void sendFireResponse(HttpExchange exchange, String consequence, boolean shipLeft) throws IOException {
        String rep = "{\"consequence\":\"" + consequence + "\", \"shipLeft\":" + shipLeft + "}";
        Validation ValidSche = new Validation();
        if(ValidSche.validateSch(rep, this.schema)){
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(202, rep.length());
        }
        else{
            rep = "Bad request";
            exchange.sendResponseHeaders(400, rep.length());}

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(rep.getBytes());}
    }

    public void handle(HttpExchange exchange) throws IOException {
        if(exchange.getRequestMethod().equals("GET")){
            String cible = exchange.getRequestURI().toString().split("\\?")[1].split("=")[1];;
            String cons = this.jeu.cF(cible);
            boolean shipLeft = this.jeu.isShipLeft();
            sendFireResponse(exchange, cons, shipLeft);
            if(!shipLeft){System.out.println("défait!"); System.exit(0);}
            try {
                String cell = this.jeu.joueur.Cible();
                String response = sendFireRequest(this.jeu.joueur.adversaryURL[0], cell);
                app(response, cell);
            }
            catch (InterruptedException e) {e.printStackTrace();}
        }
    }
    public FireHandler(Jeu jeu){
        this.jeu = jeu;
    }

}
