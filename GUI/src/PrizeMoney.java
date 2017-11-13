
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class PrizeMoney {

    private int BRIEFCASES = 26;
    public int[] initialPrizeMoneyArray = {1, 2, 5, 10, 25, 50, 75, 100, 200, 300, 400, 500, 750, 1000,
        5000, 10000, 25000, 50000, 75000, 100000, 200000, 300000, 400000, 500000, 750000, 1000000};
    int[] newPrizeMoneyArray = new int[BRIEFCASES];
    boolean[] trueFalseArray = new boolean[BRIEFCASES];
    Random randomNumber = new Random();
    static Font font = new Font("Calibri", Font.BOLD, 18);
    int x = 75;
    int y = 50;

    public PrizeMoney() {
        initialiseBriefCases();
    }

    /**
     * 
     * @return the last remaining case money value (that isn't the user's final briefcase) 
     */
    public int finalValue() {
        int finalValue = 0;
        for (int i = 0; i < BRIEFCASES; i++) {
            if (Main.briefCaseMap.getCaseOpened(i) == false && i != (Main.briefCaseMap.getUserFinalBriefCase() - 1)) {
                finalValue = newPrizeMoneyArray[i];
                break;
            }
        }
        return finalValue;
    }

    /**
     * Sets the money value at specified index to 0 so that calculations for round offers can be made
     * @param index the index of the briefcase that will have it's money value set to zero
     */
    public void setValueToZero(int index) {
        for (int i = 0; i < BRIEFCASES; i++) {
            if (getNewPrizeMoneyValue(index) == getInitialPrizeMoneyValue(i)) {
                this.initialPrizeMoneyArray[i] = 0;
                this.newPrizeMoneyArray[index] = 0;
                break;
            }
        }
    }

    /**
     * Method is used to figure out which prizeMoneyLabel will be made red when a briefcase is selected
     * @param index the index of the new prize money array
     * @return the index of the corresponding value in the initial prize money array
     */
    public int newToInitial(int index) {
        int i = 0;
        for (i = 0; i < BRIEFCASES; i++) {
            if (getNewPrizeMoneyValue(index) == getInitialPrizeMoneyValue(i)) {
                break;
            }
        }
        return i;
    }

    /**
     * Method is used to find out the new case number of an original index
     * @param index the index of the initial prize money array
     * @return the index of the corresponding value in the new prize money array
     */
    public int initialToNew(int index) {
        int i = 0;
        for (i = 0; i < 26; i++) {
            if (getInitialPrizeMoneyValue(index) == getNewPrizeMoneyValue(i)) {
                break;
            }
        }
        return i;
    }

    /**
     * Iterates through each of the 26 indexes in the initialPrizeMoneyArray and randomly assigns each value
     * to a new index in the newPrizeMoneyArray. If an index already has a value associated with it, a new
     * random number is generated until an index without a value is found
     */
    public void initialiseBriefCases() {
        for (int i = 0; i < BRIEFCASES; i++) {
            while (true) {
                int number = randomNumber.nextInt(BRIEFCASES);
                if (trueFalseArray[number] == false) {
                    newPrizeMoneyArray[number] = initialPrizeMoneyArray[i];
                    trueFalseArray[number] = true;
                    break;
                }
            }
        }
    }

    /**
     * 
     * @param index the index in the initialPrizeMoneyArray
     * @return the value stored at the index in the initialPrizeMoneyArray
     */
    public int getInitialPrizeMoneyValue(int index) {
        return this.initialPrizeMoneyArray[index];
    }

    /**
     * 
     * @param index the index in the newPrizeMoneyArray
     * @return the value stored at the index in the newPrizeMoneyArray
     */
    public int getNewPrizeMoneyValue(int index) {
        return this.newPrizeMoneyArray[index];
    }
}
