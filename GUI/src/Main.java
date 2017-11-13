
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main extends JPanel implements ActionListener {

    public static BriefCaseMap briefCaseMap = new BriefCaseMap();

    public static GUI gui;
    public static Round round;
    static PrizeMoney prizeMoney = new PrizeMoney();
    static PrizeMoneyLabels prizeMoneyLabels = new PrizeMoneyLabels();
    public static boolean buttonPressed = false;
    public static Timer timer;
    private JLabel placeHolder;
    public static boolean dealTaken;
    public static JButton dealButton, noDealButton, revealButton, showHighScoresButton;
    public static JPanel dealPanel = new JPanel();
    
    private static Font font = new Font("Calibri", Font.BOLD, 20);
    private static String playerName = "";
    private String winnings;
    private boolean lastCase = false;

    public Main() {
        super();
        timer = new Timer(2000, this);
        timer.start();
        
        Dimension d = new Dimension(100,25);
        
        dealButton = new JButton("Deal");
        dealButton.setVisible(false);
        dealButton.setBackground(Color.GREEN);
        dealButton.setPreferredSize(d);
        dealPanel.add(dealButton);
        dealButton.addActionListener(this);

        placeHolder = new JLabel("");
        placeHolder.setPreferredSize(d);
        dealPanel.add(placeHolder);

        revealButton = new JButton("Reveal");
        revealButton.setVisible(false);
        revealButton.setBackground(Color.GREEN);
        revealButton.setPreferredSize(d);
        dealPanel.add(revealButton);
        revealButton.addActionListener(this);

        noDealButton = new JButton("No Deal");
        noDealButton.setVisible(false);
        noDealButton.setPreferredSize(d);
        noDealButton.setBackground(Color.RED);
        dealPanel.add(noDealButton);
        noDealButton.addActionListener(this);

        showHighScoresButton = new JButton("Show High Scores");
        showHighScoresButton.setVisible(false);
        showHighScoresButton.setBackground(Color.GREEN);
        dealPanel.add(showHighScoresButton);
        showHighScoresButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == timer && dealTaken == true && buttonPressed == true) {
            timer.restart();
            if (Round.getCasesRemainingInRound() == 1) {
                GUI.textLabel.setText("There is " + (round.casesRemainingInRound) + " case remaining in this round");
            } else {
                GUI.textLabel.setText("There are " + (round.casesRemainingInRound) + " cases remaining in this round");
            } 
            buttonPressed = false;
            briefCaseMap.getBriefCasePanel().setVisible(true);
            if (briefCaseMap.numberOfCasesRemaining() == 2) {
                buttonPressed = true;
            }
        }

        if (source == timer && briefCaseMap.numberOfCasesRemaining() == 2 && buttonPressed == true) {
            briefCaseMap.getBriefCaseButton(briefCaseMap.finalCaseNumber()).removeActionListener(briefCaseMap);
            dealButton.setVisible(false);
            noDealButton.setVisible(false);
            dealPanel.remove(placeHolder);
            revealButton.setVisible(true);
            if (prizeMoney.getNewPrizeMoneyValue(briefCaseMap.getUserFinalBriefCase() - 1) < prizeMoney.finalValue()) {
                GUI.textLabel.setText("Does your case contain $"
                        + prizeMoney.getNewPrizeMoneyValue(briefCaseMap.getUserFinalBriefCase() - 1) + " or $"
                        + prizeMoney.finalValue());
            } else {
                GUI.textLabel.setText("Does your case contain $" + prizeMoney.finalValue() + " or $"
                        + prizeMoney.getNewPrizeMoneyValue(briefCaseMap.getUserFinalBriefCase() - 1));
            }
            briefCaseMap.getBriefCasePanel().setVisible(true);
        }

        if (source == dealButton) {
            GUI.textLabel.setText("It's a deal for $" + round.currentOffer + " congrats " + this.playerName);
            dealTaken = true;
            winnings = "$" + round.currentOffer;
            try {
                JDBC jdbc = new JDBC(getPlayerName(), round.currentOffer);
            } catch (Exception ex) {

            }
        }

        if (source == noDealButton) {
            GUI.textLabel.setText("No Deal!!!");
        }

        if (source == revealButton) {
            Font newFont = (new Font("Calibri", Font.BOLD, 18));
            buttonPressed = false;

            GUI.textLabel.setText("Briefcase " + (briefCaseMap.getUserFinalBriefCase()) + " contains $"
                    + prizeMoney.getNewPrizeMoneyValue(briefCaseMap.getUserFinalBriefCase() - 1));
            for (int i = 0; i < 26; i++) {
                if (i != prizeMoney.newToInitial(briefCaseMap.getUserFinalBriefCase() - 1)) {
                    if (i < 13) {
                        prizeMoneyLabels.westLabel[i]
                                .setBackground(Color.RED);
                    } else {
                        prizeMoneyLabels.eastLabel[i - 13]
                                .setBackground(Color.RED);
                    }
                }
                JButton currentCase = briefCaseMap.getBriefCaseButton(i);
                if (i != briefCaseMap.getUserFinalBriefCase() - 1) {
                    currentCase.setBackground(Color.LIGHT_GRAY);
                }
                if (i == (briefCaseMap.getUserFinalBriefCase() - 1) || i == briefCaseMap.finalCaseNumber()) {
                    currentCase.setFont(newFont);
                    String pendingText = "$" + prizeMoney.getNewPrizeMoneyValue(i);
                    currentCase.setText(pendingText);
                }
            }

            if (!dealTaken) {
                try {
                    winnings = "$" + prizeMoney.getNewPrizeMoneyValue(briefCaseMap.getUserFinalBriefCase() - 1);
                    JDBC jdbc = new JDBC(getPlayerName(),
                            prizeMoney.getNewPrizeMoneyValue(briefCaseMap.getUserFinalBriefCase() - 1));
                } catch (Exception ex) {

                }
            }
            revealButton.setVisible(false);
            showHighScoresButton.setVisible(true);
        }

        if (source == showHighScoresButton) {
            HighScoreGUI hs = new HighScoreGUI(this.playerName, winnings);
        }

        if (source == dealButton || source == noDealButton) {
            if (round.initialCasesInRound > 1) {
                round.setCasesInRound(round.initialCasesInRound - 1);
            } else if (briefCaseMap.numberOfCasesRemaining() > 2) {
                round.setCasesInRound(round.initialCasesInRound);
            }
            dealButton.setVisible(false);
            noDealButton.setVisible(false);
            buttonPressed = true;
            e.setSource(timer);
            timer.restart();
        }

        if (source == timer && buttonPressed == true && dealTaken == true) {
            buttonPressed = false;
            timer.restart();
        }

        if (source == timer && round.initialCasesInRound > 1 && buttonPressed == true) {
            GUI.textLabel.setText("There are " + (round.casesRemainingInRound) + " cases remaining in this round");
            buttonPressed = false;
            briefCaseMap.getBriefCasePanel().setVisible(true);
        } else if (source == timer && briefCaseMap.numberOfCasesRemaining() > 2 && buttonPressed == true) {
            GUI.textLabel.setText("There is " + (round.casesRemainingInRound) + " case remaining in this round");
            buttonPressed = false;
            briefCaseMap.getBriefCasePanel().setVisible(true);
        }
    }

    /**
     * The user is prompted to enter their name into an input dialog box
     * The user name is then updated
     */
    public void setPlayerName() {
        while (this.playerName == "") {
            setPlayerName(JOptionPane.showInputDialog("Please enter your name "));
            if (playerName.length() == 0) {
                this.playerName = "";
                JOptionPane.showMessageDialog(null, "Name cannot be empty");
            } else {
                JOptionPane.showMessageDialog(null, "Welcome to Deal or No Deal " + this.playerName);
            }
        }
    }
    
    /**
     * 
     * @param name sets the current player's name
     */
    public void setPlayerName(String name) {
        this.playerName = name;
    }
    
    /**
     * 
     * @return the current player's name
     */
    public String getPlayerName() {
        return this.playerName;
    }

    public static void main(String[] args) {
        Main main = new Main();
        
        main.setPlayerName();
        main.gui = new GUI();
        briefCaseMap.selectUserBriefCase();
        round.setCasesInRound(6);
        
//        Error checking that tells me where the highest and lowest values are located
//        JOptionPane.showMessageDialog(gui.frame, "The $1,000,000 is in case " + (prizeMoney.initialToNew(25) + 1));
//        JOptionPane.showMessageDialog(gui.frame, "The $1 is in case " + (prizeMoney.initialToNew(0) + 1));
    }
}
