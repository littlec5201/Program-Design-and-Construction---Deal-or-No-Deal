
import java.awt.*;
import java.awt.event.*;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class BriefCaseMap extends JPanel implements ActionListener {
    PrizeMoneyLabels prizeMoneyLabels;
    GUI gui;
    Round round = new Round();
    private boolean userPickingBriefCase = true;
    private boolean[] caseOpened = new boolean[26];
    private JButton briefCaseButton[] = new JButton[26];
    private JPanel briefCasePanel = new JPanel();
    private static Font font = new Font("Calibri", Font.BOLD, 20);
    private int userFinalBriefCase = 100;
    
    /**
     * 
     * @return the integer value of the user's final briefcase 
     */
    public int getUserFinalBriefCase() {
        return this.userFinalBriefCase;
    }
    
    /**
     * 
     * @return the panel that the briefcase buttons are contained in
     */
    public JPanel getBriefCasePanel() {
        briefCasePanel.setLayout(new GridLayout(4, 7, 15, 15));
        briefCasePanel.setBorder(new EmptyBorder(115, 25, 75, 25));
        return this.briefCasePanel;
    }
    
    /**
     * 
     * @param caseNumber specifies which brief case will be selected  
     * @return the button at the index of caseNumber
     */
    public JButton getBriefCaseButton(int caseNumber) {
        return this.briefCaseButton[caseNumber];
    }
    
    /**
     * Default constructor for the BriefCaseMap class
     * 
     */
    public BriefCaseMap() {
        for (int i = 0; i < 26; i++) {
            if (!getCaseOpened(i)) {
                briefCaseButton[i] = new JButton("" + (i + 1)) {
                    {
                        setFont(font);
                        setOpaque(true);
                        setForeground(Color.BLACK);
                        setBackground(new Color(135, 206, 235));
                        setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    }
                };
                if (i == 21) {
                    briefCasePanel.add(new JLabel());
                }
                briefCasePanel.add(briefCaseButton[i]);
            }
            briefCaseButton[i].addActionListener(this);
        }
    }

    /**
     * 
     * @return the final remaining briefcase number (that isn't the user's final briefcase)
     */
    public int finalCaseNumber() {
        int finalCaseNumber = 0;
        for (int i = 0; i < 26; i++) {
            if (caseOpened[i] == false && i != (userFinalBriefCase - 1)) {
                finalCaseNumber = i;
                break;
            }
        }
        return finalCaseNumber;
    }

    /**
     * 
     * @return the number of briefcases remaining in the game
     */
    public int numberOfCasesRemaining() {
        int casesRemaining = 0;
        for (int i = 0; i < 26; i++) {
            if (getCaseOpened(i) == false) {
                casesRemaining++;
            }
        }
        return casesRemaining;
    }

    /**
     * Method that prompts the user to select their final briefcase
     */
    public void selectUserBriefCase() {
        GUI.textLabel.setFont(font);
        GUI.textPanel.setBackground(new Color(135, 206, 235));
        GUI.textLabel.setText("Please select your final briefcase");
        GUI.textPanel.add(GUI.textLabel);
    }

    /**
     * 
     * @return whether the requested case has been opened or not
     */
    public boolean getCaseOpened(int i) {
        return this.caseOpened[i];
    }

    /**
     * 
     * @param caseOpened sets the case opened status to "true"
     */
    public void setCaseOpened(int caseOpened) {
        this.caseOpened[caseOpened] = true;
    }

    /**
     * Method listens for events such as the timer and buttons being clicked
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        
//        Removes the action listeners for all of the cases when only two cases remain
//        A button is displayed to show the contents of the two remaining cases
//        This is done in the Main class however
        if (numberOfCasesRemaining() == 2) {
            for (int i = 0; i < 26; i++) {
                briefCaseButton[i].removeActionListener(this);
            }
        }
        else if (round.casesRemainingInRound > 0) {
            for (int i = 0; i < 26; i++) {
                if (source == briefCaseButton[i]) {
                    if (source == briefCaseButton[i] && userPickingBriefCase == true) {
                        GUI.textLabel.setText("There are 6 cases remaining in this round");
                        briefCaseButton[i].setBackground(new Color(255, 215, 0));
                        userFinalBriefCase = (i + 1);
                        userPickingBriefCase = false;
                    } else {
                        if (Main.prizeMoney.newToInitial(i) < 13) {
                            prizeMoneyLabels.westLabel[Main.prizeMoney.newToInitial(i)].setBackground(Color.RED);
                        }
                        if (Main.prizeMoney.newToInitial(i) > 12) {
                            prizeMoneyLabels.eastLabel[Main.prizeMoney.newToInitial(i) - 13].setBackground(Color.RED);
                        }
                        briefCaseButton[i].setFont(new Font("Calibri", Font.BOLD, 18));
                        briefCaseButton[i].setText("$" + Main.prizeMoney.getNewPrizeMoneyValue(i));
                        briefCaseButton[i].setBackground(Color.LIGHT_GRAY);
                        setCaseOpened(i);
                        Main.prizeMoney.setValueToZero(i);
                        round.casesRemaining();
                    }
                    briefCaseButton[i].removeActionListener(this);
                }
            }
        } else {
            round.casesRemaining();
        }
    }
}
