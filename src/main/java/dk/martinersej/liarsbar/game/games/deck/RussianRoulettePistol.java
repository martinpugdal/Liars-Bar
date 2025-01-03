package dk.martinersej.liarsbar.game.games.deck;

import java.util.Random;

public class RussianRoulettePistol {

    private final boolean[] chambers;
    private int currentPosition;

    public RussianRoulettePistol() {
        chambers = new boolean[6];
        setupChambers();
    }

    private void setupChambers() {
        int bulletPosition = new Random().nextInt(6);
        chambers[bulletPosition] = true;
        currentPosition = 0;
    }

    public boolean pullTrigger() {
        return chambers[currentPosition++];
    }
}