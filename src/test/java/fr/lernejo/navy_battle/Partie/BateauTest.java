package fr.lernejo.navy_battle.Partie;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BateauTest {
    @Test
    void verify_Sunk_true(){
        Bateau bateau = new Bateau(1, new int[]{-1}, new int[]{-1});
        Assertions.assertThat(bateau.isSunk())
            .as("Test if bateau is sunk - true")
            .isEqualTo(true);
    }

    @Test
    void verify_Sunk_falee(){
        Bateau bateau = new Bateau(2, new int[]{-1, 0}, new int[]{-1, 1});
        Assertions.assertThat(bateau.isSunk())
            .as("Test if bateau is sunk - false")
            .isEqualTo(false);
    }

}
