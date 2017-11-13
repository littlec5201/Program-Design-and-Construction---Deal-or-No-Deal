
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Round {

    GUI gui;
    BriefCaseMap briefCaseMap;
    public static int initialCasesInRound;
    public static int casesRemainingInRound;
    private static int highestOffer = 0;
    public static int currentOffer = 0;
    public static int offerTaken = 0;

    public Round() {

    }

    /**
     * 
     * @param offerTaken the value of the offer taken by the current player
     */
    public void setOfferTaken(int offerTaken) {
        this.offerTaken = offerTaken;
    }

    /**
     * 
     * @return the offer taken by the current player
     */
    public int getOfferTaken() {
        return this.offerTaken;
    }

    /**
     * 
     * @param casesInRound the number of cases the round will begin with
     */
    public static void setCasesInRound(int casesInRound) {
        initialCasesInRound = casesInRound;
        casesRemainingInRound = initialCasesInRound;
    }

    /**
     * 
     * @return an offer based on the number of remaining cases in the game and the total amount of prize money
     * left in play (total prize money / number of cases remaining)
     */
    public int makeOffer() {
        int runningTotal = 0;
        int noOfCasesLeft = 0;
        for (int i = 0; i < 26; i++) {
            if (Main.briefCaseMap.getCaseOpened(i) == false) {
                noOfCasesLeft++;
            }
            runningTotal += Main.prizeMoney.newPrizeMoneyArray[i];
        }
        if ((runningTotal / noOfCasesLeft) > highestOffer) {
            highestOffer = runningTotal / noOfCasesLeft;
        }
        currentOffer = runningTotal / noOfCasesLeft;
        return currentOffer;
    }

    /**
     * Updates the text in the GUI text label after each briefcase is opened. The text will be either changed to 
     * the number of cases remaining in the round, or an offer will be presented. After each case is opened, the 
     * number of cases remaining in the round decreases by one
     */
    public void casesRemaining() {
        if (casesRemainingInRound == 1) {
            if (Main.dealTaken == false) {
                GUI.textLabel.setText("I am willing to offer you $" + makeOffer() + "... Deal or No Deal?");
                Main.briefCaseMap.getBriefCasePanel().setVisible(false);
                Main.dealButton.setVisible(true);
                Main.noDealButton.setVisible(true);
            }
            if (Main.dealTaken == true) {
                GUI.textLabel.setText("You would have been offered $" + makeOffer());
                Main.briefCaseMap.getBriefCasePanel().setVisible(false);
                if (initialCasesInRound > 1) {
                    setCasesInRound(initialCasesInRound - 1);
                } else if (Main.briefCaseMap.numberOfCasesRemaining() > 2) {
                    setCasesInRound(initialCasesInRound);
                }
                Main.buttonPressed = true;
                Main.timer.restart();
            }
        } else {
            casesRemainingInRound--;
            if (casesRemainingInRound == 1) {
                GUI.textLabel.setText("There is " + (casesRemainingInRound) + " case remaining in this round");
            } else {
                GUI.textLabel.setText("There are " + (casesRemainingInRound) + " cases remaining in this round");
            }
        }
    }
    
    /**
     * 
     * @return the number of cases remaining in the current round
     */
    public static int getCasesRemainingInRound() {
        return casesRemainingInRound;
    }
    
    /**
     * 
     * @return how many cases there were to be opened at the beginning of the round
     */
    public static int getInitialCasesInRound() {
        return initialCasesInRound;
    }
}
