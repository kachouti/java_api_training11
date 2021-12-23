package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;
import fr.lernejo.navy_battle.Partie.Jeu;
import fr.lernejo.navy_battle.Partie.Joueur;
import fr.lernejo.navy_battle.Utils.PostRequest;
import org.apache.commons.validator.routines.UrlValidator;

public class Launcher {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {System.out.println("enrez le port!"); System.exit(1);}
        try{
            final int port = Integer.parseInt(args[0]);
            final Joueur joueur = new Joueur();
            final  Jeu jeu = new Jeu(joueur);
           final  HttpServer httpServer = new Serveur().launch(port, jeu); httpServer.start();
            jeu.jeuD();
            if (args.length > 1){
                UrlValidator urlValidator = new UrlValidator(UrlValidator.ALLOW_LOCAL_URLS);
                if(!urlValidator.isValid(args[1])){ System.out.println("Adversary's URL " + args[1] + " isn't valid !");
                    System.exit(1);}
                jeu.joueur.adversaryURL[0] = args[1];
                PostRequest postRequest = new PostRequest(port); postRequest.sendPostRequest(args[1]);
            }
        } catch (Exception e){throw new NumberFormatException(e.getMessage());}
    }
}
