package de.hs.emden.stud.busch;

import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Abstract class for implementing console games.
 *
 * @author Nico Busch (nbusch@technik-emden.de)
 */
public abstract class ConsoleGame implements IConsoleGame {

    /**
     * Scanner object for reading input from Console.
     */
    protected Scanner sc;

    /**
     * Ctor
     *
     * @param inputStream Input stream for user input
     */
    public ConsoleGame(InputStream inputStream) {
        sc = new Scanner(inputStream);
    }

    /**
     * Ctor
     */
    public ConsoleGame() {
        // if no input stream for user input is given, use standard System.in to read from console
        this(System.in);
    }

    /**
     * Print a message to console.
     *
     * @param message message to print
     */
    protected void printMessage(String message) {
        System.out.println(message);
    }

    /**
     * * Ask user to input text.
     * In this case the two values given in positiveDecisionValue and negativeDecisionValue are possible.
     * If positiveDecisionValue is entered by user, the function will return true.
     * If negativeDecisionValue is entered by user, the function will return false.
     * If the user simply press return, the defaultDecision Value will will be returned, if this is given..
     *
     * @param message               message to display before asking user for input
     * @param positiveDecisionValue the value the user has to enter to return a positive response (true)
     * @param negativeDecisionValue the value the user has to enter to return a negative response (false)
     * @param defaultDecision       the default value which will be returned if user simply press enter.
     *
     * @return  true if user entered positiveDecisionValue
     *          false if user entered negativeDecisionValue
     *          defaultDecision if user pressed ENTER
     */
    protected boolean askForUserInput(String message, String positiveDecisionValue, String negativeDecisionValue, Boolean defaultDecision) {
        printMessage(message);

        return askForUserInput(positiveDecisionValue, negativeDecisionValue, defaultDecision);
    }

    /**
     * Ask user to input text.
     * In this case the two values given in positiveDecisionValue and negativeDecisionValue are possible.
     * If positiveDecisionValue is entered by user, the function will return true.
     * If negativeDecisionValue is entered by user, the function will return false.
     * If the user simply press return, the defaultDecision Value will will be returned, if this is given..
     *
     * @param positiveDecisionValue the value the user has to enter to return a positive response (true)
     * @param negativeDecisionValue the value the user has to enter to return a negative response (false)
     * @param defaultDecision       the default value which will be returned if user simply press enter.
     *
     * @return  true if user entered positiveDecisionValue
     *          false if user entered negativeDecisionValue
     *          defaultDecision if user pressed ENTER
     */
    protected boolean askForUserInput(String positiveDecisionValue, String negativeDecisionValue, Boolean defaultDecision) {
        String decision = sc.nextLine();
        if (decision.equalsIgnoreCase(positiveDecisionValue)) {
            return true;
        }
        else if (decision.equalsIgnoreCase(negativeDecisionValue)) {
            return false;
        }
        else if (defaultDecision != null && decision.equalsIgnoreCase("")) {
            return defaultDecision.booleanValue();
        }

        printMessage(
                String.format(
                        "Ihre Eingabe war ungültig. Sie müssen mit '%s' oder '%s' antworten",
                        positiveDecisionValue,
                        negativeDecisionValue
                )
        );

        return askForUserInput(positiveDecisionValue, negativeDecisionValue, defaultDecision);
    }

    /**
     * Ask user to input text.
     * In this case integer values between minValidUserInputValue and maxValidUserInputValue parameters
     *
     * @param message   message to present to user
     * @param minValidUserInputValue       minValidUserInputValue int value user can input
     * @param maxValidUserInputValue       maxValidUserInputValue int value user can input
     *
     * @return          user input value
     */
    protected int askForUserInput(String message, int minValidUserInputValue, int maxValidUserInputValue) {
        printMessage(message);

        return askForUserInput(minValidUserInputValue, maxValidUserInputValue);
    }

    /**
     * Ask user to input text.
     * In this case integer values between minValidUserInputValue and maxValidUserInputValue parameters
     *
     * @param minValidUserInputValue       minValidUserInputValue int value user can input
     * @param maxValidUserInputValue       maxValidUserInputValue int value user can input
     *
     * @return          user input value
     */
    protected int askForUserInput(int minValidUserInputValue, int maxValidUserInputValue) {
        int userInputValue;

        try {
            userInputValue = sc.nextInt();
        } catch (InputMismatchException e) {
            printMessage("Bitte geben Sie nur Ganzzahlen ein.");
            sc.nextLine();

            return askForUserInput(minValidUserInputValue, maxValidUserInputValue);
        }

        if (userInputValue < minValidUserInputValue || userInputValue > maxValidUserInputValue) {
            printMessage(
                    String.format(
                            "Bitte geben Sie eine Zahl zwischen %d und %d ein.",
                            minValidUserInputValue,
                            maxValidUserInputValue
                    )
            );

            return askForUserInput(minValidUserInputValue, maxValidUserInputValue);
        }

        return userInputValue;
    }
}
