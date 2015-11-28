package de.hs.emden.stud.busch;

import java.io.InputStream;

/**
 * @author Nico Busch (nbusch@technik-emden.de)
 */
public class MatchGame extends ConsoleGame {
    /**
     * How many matches do we have to take at least in one move
     */
    private static final int MINMATCHPERMOVE = 1;

    /**
     * How many matches do we have to take at most in one move
     */
    private static final int MAXMATCHPERMOVE = 3;

    /**
     * Minimum matches to start playing a game.
     * If user wants to select count matches, he/she has to select count matches between
     * MINMATCHES and MAXMATCHES
     *
     * This isn't a strict value. You can increase of decrease the value.
     *
     * CONSTRAINT: MINMATCHES must be smaller than MAXMATCHES
     */
    private static final int MINMATCHES = 10;

    /**
     * Maximum matches to start playing a game
     * This isn't a strict value. You can increase of decrease the value.
     *
     * CONSTRAINT: MINMATCHES must be smaller than MAXMATCHES
     */
    private static final int MAXMATCHES = 50;

    /**
     * Default matches when the computer should select the count matches.
     */
    private static final int DEFAULTMATCHCOUNT = 17;

    /**
     * Ctor
     *
     * @param io    Input stream for user input
     */
    public MatchGame(InputStream io) {
        super(io);
    }

    /**
     * Ctor
     */
    public MatchGame() {
        super();
    }

    /**
     * Start playing the game.
     */
    public void play() {
        printMessage("Willkommen zum Streichholzspiel.");

        // as long as the user doesn't want to exit the game, start a new game.
        while (startNewGame()) {
            printMessage("Starte neues Spiel.");
        }
    }

    /**
     * Start playing a new game.
     *
     * @return  true if user wants to play a new game,
     *          false if user wants to exit the game
     */
    private boolean startNewGame() {
        printMessage("Wollen Sie mit dem Spiel beginnen? Dann antworten Sie mit 'ja'.");
        printMessage("Falls Sie mit 'nein' antworten, dürfen Sie die Anzahl Streichhölzer festlegen. [ja]");

        int remainingMatchesCount = DEFAULTMATCHCOUNT;
        boolean doesComputerStartsGame = false;

        if (askForUserInput("ja", "nein", true)) {
            // user wants to start the game
            printMessage("Sie beginnen das Spiel.");
        }
        else {
            // user wants to define the match count
            printMessage("Sie legen die Anzahl Streichhölzer fest.");
            remainingMatchesCount = askForUserInput(
                    "Bitte geben Sie die Anzahl Streichhölzer ein. Der Wert muss zwischen 10 und 50 liegen.",
                    MINMATCHES,
                    MAXMATCHES
            );
            // if user wants to define the starting match count, the computer starts the game
            doesComputerStartsGame = true;
        }

        printMessage(String.format("Spiel beginnt mit %d Streichhölzern.", remainingMatchesCount));

        // play this specific game with set match count
        playGame(doesComputerStartsGame, remainingMatchesCount);

        sc.nextLine();

        return askForUserInput("Noch ein Spiel? [nein]", "ja", "nein", false);
    }

    /**
     * Start playing a new specific game with given parameters
     *
     * @param doesComputerStartsGame    Should the computer start playing
     * @param remainingMatchesCount     remaining matches
     */
    private void playGame(boolean doesComputerStartsGame, int remainingMatchesCount) {

        // if the computer should start the game
        if (doesComputerStartsGame) {
            remainingMatchesCount -= playComputer(remainingMatchesCount);

            // if the remaining matches are less or equal 1, than show a message and return.
            // Game is finished.
            if (remainingMatchesCount <= 1) {
                printMessage("Benutzer hat verloren.");

                return;
            }
        }

        remainingMatchesCount -= playUser();

        // if the remaining matches are less or equal 1, than show a message and return.
        // Game is finished.
        if (remainingMatchesCount <= 1) {
            printMessage("Computer hat verloren.");

            return;
        }

        playGame(true, remainingMatchesCount);
    }

    /**
     * Play the user step.
     * Ask user to input count matches to take.
     *
     * @return  taken matches by user
     */
    private int playUser() {
        return askForUserInput(
                String.format(
                        "Bitte Anzahl zu ziehender Streichhölzer eingeben. Werte zwischen %d und %d erlaubt.",
                        MINMATCHPERMOVE,
                        MAXMATCHPERMOVE
                ),
                MINMATCHPERMOVE, MAXMATCHPERMOVE
        );
    }

    /**
     * Play the computer step.
     *
     * @param remainingMatchesCount remaining matches
     *
     * @return  taken matches by computer
     */
    protected int playComputer(int remainingMatchesCount) {
        for (int i = MAXMATCHPERMOVE; i >= MINMATCHPERMOVE; i--) {
            if ((remainingMatchesCount - i) % (MAXMATCHPERMOVE + 1) == 1) {
                printMessage(String.format("Computer zieht %d Streichhölzer.", i));
                return i;
            }
        }

        return 1;
    }
}
