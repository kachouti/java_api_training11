package fr.lernejo.navy_battle.Partie;

import java.util.*;

public class Joueur {
    public final String[] adversaryURL = new String[1];
    public final int[][] oc1 = new int[10][10];
    public final String[][] oc2 = new String[10][10];
    public final List<Bateau> liste_Bateau = new ArrayList<>();

    public void ocDeb(){
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){this.oc1[i][j] = 0; this.oc2[i][j] = "o";}
        }
    }

    public String Cible(){
        String cell; int a; int b;
        do{
            a = new Random().nextInt(10);
            b = new Random().nextInt(10);
            cell = (char)(b+65) + "" + (a+1);
        }while(!verifieCel(a, b, cell));
        return cell;
    }
    public boolean valid(int taille, int dir, int a, int b){
        if(!((b<=9 && b>=0) && (a<=9 && a>=0))){ return false;}
        for(int i=0; i<taille; i++) {
            if (dir == 0)
                if (this.oc1[a][b+i] != 0 || (b - 1) + taille > 9) {
                    return false;
                }
            if (dir == 1)
                if (this.oc1[a+i][b] != 0 || ((a) - 1) + taille > 9) {
                    return false;
                }
        }
        return true;
    }
    public void bateauS(Bateau.typeBateau typeBateau, int dir, int a, int b){
        int[] x = new int[typeBateau.taille]; int[] y = new int[typeBateau.taille];
        for(int i = 0; i<typeBateau.taille; i++){
            if(dir == 0){x[i] = (a); y[i] = (b+i);
                this.oc1[a][b+i] = typeBateau.taille;
            }
            if(dir == 1){x[i] = a+i; y[i] = b;
                this.oc1[a+i][b] = typeBateau.taille;
            }
        }
        this.liste_Bateau.add(new Bateau(typeBateau.taille, x, y));
    }
    public boolean verifieCel(int a, int b, String cell){
        if(this.oc2[a][b].equals("o") && cell.matches("^[A-J]{1}([1-9]|10)$"))
            return true;


        return false;
    }



    public void bateauDir(Bateau.typeBateau typeBateau){

       final int dir = (int) Math.round(Math.random());
       int a; int b;
        do{
            a = new Random().nextInt(10);
            b = new Random().nextInt(10);
        }while(!valid(typeBateau.taille, dir, a, b));
        bateauS(typeBateau, dir, a, b);
    }


}
