package fr.lernejo.navy_battle.Handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import fr.lernejo.navy_battle.Partie.Jeu;
import fr.lernejo.navy_battle.Utils.Validation;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class PostHandler implements HttpHandler  {
    private final Jeu jeu;

    private final String schema = "{\"$schema\": \"http://json-schema.org/schema#\",\"type\": \"object\",\"properties\": " +
        "{\"id\": {\"type\": \"string\"},\"url\": {\"type\": " +
        "\"string\"},\"message\": {\"type\": \"string\"}},\"required\": " +
        "[\"id\",\"url\",\"message\"]}";



    public String sendPOSTResponse(HttpExchange exchange) throws IOException {
        final String res;
        Validation validation = new Validation();
        JSONObject js = reqJson(exchange);
        if(validation.validateSch(js.toString(), this.schema)){
            final int port = exchange.getHttpContext().getServer().getAddress().getPort();
            res = "{\"id\":\"0\", \"url\":\"http://localhost:" + port +
                "\", \"message\":\"Response from Serveur\"}";
            exchange.sendResponseHeaders(202, res.length());
        }
        else{ res = "Bad request"; exchange.sendResponseHeaders(400, res.length());}
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(res.getBytes());}
        return js.getString("url");
    }

    public JSONObject reqJson(HttpExchange exchange) throws IOException {
        InputStreamReader input = new InputStreamReader(exchange.getRequestBody());
        BufferedReader br = new BufferedReader(input);
        String resu = br.readLine();
        return new JSONObject(resu);
    }


    public void handle(HttpExchange exchange) throws IOException {
        if(exchange.getRequestMethod().equals("POST")){
            String adversaryURL = sendPOSTResponse(exchange);
            System.out.println("The game starts!");
            this.jeu.joueur.adversaryURL[0] =  adversaryURL;
            System.out.println("Enter a cell to target:");
            String cell = this.jeu.joueur.Cible();
            FireHandler fire = new FireHandler(this.jeu);
            try {
                String response = fire.sendFireRequest(adversaryURL, cell);
                fire.app(response, cell);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public PostHandler(Jeu jeu){
        this.jeu = jeu;
    }

}
