package fr.lernejo.navy_battle.Partie;

import java.io.IOException;

public class Jeu {
    public final Joueur joueur;
    public Jeu(Joueur joueur){
        this.joueur = joueur;
    }

    public void jeuD() throws IOException {
        this.joueur.ocDeb();
        for(Bateau.typeBateau typeBoat : Bateau.typeBateau.values()){
            this.joueur.bateauDir(typeBoat);
        }
    }



    public String cF(String cellTargeted){
        int a = Integer.parseInt(cellTargeted.replaceAll("[A-Z]", "")) - 1; int b = (int)cellTargeted.charAt(0)-65;
        if(this.joueur.oc1[a][b] == 0) return "miss";
        if(this.joueur.oc1[a][b] >= 1){
            Bateau bateau;
            for(int i = 0; i<this.joueur.liste_Bateau.toArray().length; i++){
                for(int j = 0; j<this.joueur.liste_Bateau.get(i).taille; j++){
                    if(this.joueur.liste_Bateau.get(i).x_abcisse[j] == a && this.joueur.liste_Bateau.get(i).y_Ordonne[j] == b){
                        this.joueur.liste_Bateau.get(i).x_abcisse[j]=-1; this.joueur.liste_Bateau.get(i).y_Ordonne[j]=-1;
                        bateau = this.joueur.liste_Bateau.get(i);
                        this.joueur.oc1[a][b] = 0;
                        if(bateau.isSunk()) return "sunk";
                        else return "hit";}}}}
        return "error";
    }

    public void bateauD(){
        StringBuilder b = new StringBuilder();
        for(int x=0; x<2; x++){
            b.append("  ");
            for (int i=0; i< 10; i++) b.append(" ").append((char)(i+65));
        }

    }
    public boolean isShipLeft(){
        for(int i = 0; i<this.joueur.liste_Bateau.toArray().length; i++){
            for (int j = 0; j<this.joueur.liste_Bateau.get(i).taille; j++){
                if(this.joueur.liste_Bateau.get(i).x_abcisse[j] >= 0 || this.joueur.liste_Bateau.get(i).y_Ordonne[j] >= 0)
                    return true;
            }
        }
        return false;
    }

    public void bateauMod(String cons, String cell){
        int b = ((int)cell.charAt(0))-65;
        int a = Integer.parseInt(cell.replaceAll("[A-Z]", "")) - 1;
        if(cons.equals("miss"))
            this.joueur.oc2[a][b] = "x";

        if(cons.equals("hit") || cons.equals("sunk"))
            this.joueur.oc2[a][b] = "H";
    }
}
