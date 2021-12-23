package fr.lernejo.navy_battle.Partie;

public class Bateau {
    public final int taille;
    public final int[] x_abcisse;
    public final int[] y_Ordonne;

    public Bateau(int taille, int[] x_abcisse, int[] y_Ordonne){
        this.taille = taille;
        this.x_abcisse = x_abcisse;
        this.y_Ordonne = y_Ordonne;
    }

    public enum typeBateau{
        pv(5), cr(4), cp1(3), cp2(3), tp(2);
        public final int taille;
        typeBateau(int taille){
            this.taille = taille;
        }
    }

    public boolean isSunk(){
        for(int i=0; i<this.taille; i++){
            if(this.x_abcisse[i] >= 0 || this.y_Ordonne[i] >= 0)
                return false;
        }
        return true;
    }
}
