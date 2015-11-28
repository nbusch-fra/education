package de.hs.emden.stud.busch;

/**
 * Main class for starting the match game.
 *
 * @author Nico Busch (nbusch@technik-emden.de)
 */
public class Main {

    /**
     * Start playing the match game.
     *
     * @param args Command line arguments. Not used here.
     */
    public static void main(String[] args) {
        MatchGame matchGame = new MatchGame();
        matchGame.play();
    }
}
