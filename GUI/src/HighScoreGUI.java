
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Callum
 */
public class HighScoreGUI {

    private JFrame highScoreFrame = new JFrame("High Scores");
    private JPanel infoPanel = new JPanel();
    private JPanel scoresPanel = new JPanel();
    private JLabel positionLabel = new JLabel("Position");
    private JLabel nameLabel = new JLabel("Player Name");
    private JLabel winningsLabel = new JLabel("Winnings");
    private JLabel dateLabel = new JLabel("Date");
    private JLabel positionDataLabel[] = new JLabel[10];
    private JLabel nameDataLabel[] = new JLabel[10];
    private JLabel winningsDataLabel[] = new JLabel[10];
    private JLabel dateDataLabel[] = new JLabel[10];
    private boolean addedToScoreboard = false;
    private int height = 525;
    private int width = 800;

    public HighScoreGUI(String activeName, String activeWinnings) {
        infoPanel.setSize(new Dimension(width, 50));
        infoPanel.setPreferredSize(infoPanel.getSize());
        infoPanel.setMinimumSize(infoPanel.getSize());
        infoPanel.setMaximumSize(infoPanel.getSize());
        infoPanel.setLayout(new GridLayout(1, 4));
        infoPanel.add(headingLabel(positionLabel));
        infoPanel.add(headingLabel(nameLabel));
        infoPanel.add(headingLabel(winningsLabel));
        infoPanel.add(headingLabel(dateLabel));

        scoresPanel.setSize(new Dimension(width, 450));
        scoresPanel.setPreferredSize(scoresPanel.getSize());
        scoresPanel.setMinimumSize(scoresPanel.getSize());
        scoresPanel.setMaximumSize(scoresPanel.getSize());
        scoresPanel.setLayout(new GridLayout(10, 4));
        try {
            JDBC jdbc = new JDBC();
            Color color1 = new Color(255, 215, 0);
            Color color2 = new Color(135, 206, 235);
            Color highScoreColor = new Color(255,20,147);
            
            int count = 10;
            for (int i = 0; i < count; i++) {
                    String name = jdbc.getName(i);
                    String winnings = jdbc.getWinnings(i);
                    String date = jdbc.getDate(i);
                if (activeName.equals(name) && activeWinnings.equals(winnings) && addedToScoreboard == false) {
                    scoresPanel.add(scoreLabel(positionDataLabel[i], "" + (i + 1), highScoreColor));
                    scoresPanel.add(scoreLabel(nameDataLabel[i], name, highScoreColor));
                    scoresPanel.add(scoreLabel(winningsDataLabel[i], winnings, highScoreColor));
                    scoresPanel.add(scoreLabel(dateDataLabel[i], date, highScoreColor));
                    JOptionPane.showMessageDialog(highScoreFrame, "Congratulations " + name + ". You managed to make it onto the leaderboard!");
                    addedToScoreboard = true;
                } else if (i % 2 == 0) {
                    scoresPanel.add(scoreLabel(positionDataLabel[i], "" + (i + 1), color1));
                    scoresPanel.add(scoreLabel(nameDataLabel[i], name, color1));
                    scoresPanel.add(scoreLabel(winningsDataLabel[i], winnings, color1));
                    scoresPanel.add(scoreLabel(dateDataLabel[i], date, color1));
                } else {
                    scoresPanel.add(scoreLabel(positionDataLabel[i], "" + (i + 1), color2));
                    scoresPanel.add(scoreLabel(nameDataLabel[i], name, color2));
                    scoresPanel.add(scoreLabel(winningsDataLabel[i], winnings, color2));
                    scoresPanel.add(scoreLabel(dateDataLabel[i], date, color2));
                }
            }
            jdbc.shutdown();
        } catch (Exception e) {
            
        }
        highScoreFrame.add(infoPanel, BorderLayout.NORTH);
        highScoreFrame.add(scoresPanel, BorderLayout.SOUTH);
        highScoreFrame.pack();
        highScoreFrame.setSize(new Dimension(width, height));
        highScoreFrame.setResizable(false);
        highScoreFrame.setVisible(true);
    }

    /**
     * 
     * @param label is the label that will be modified
     * @return the modified label
     */
    public JLabel headingLabel(JLabel label) {
        label = new JLabel(label.getText(), SwingConstants.CENTER) {
            {
                setSize(50, 300);
                setPreferredSize(getSize());
                setMinimumSize(getSize());
                setMaximumSize(getSize());
                setOpaque(true);
                setBackground(Color.LIGHT_GRAY);
            }
        };
        return label;
    }

    /**
     * 
     * @param label the label that will be modified
     * @param text the text displayed on the label
     * @param color the color of the label
     * @return a new label based on the parameters
     */
    public JLabel scoreLabel(JLabel label, String text, Color color) {
        label = new JLabel(text, SwingConstants.CENTER) {
            {
                setSize(25, 300);
                setPreferredSize(getSize());
                setMinimumSize(getSize());
                setMaximumSize(getSize());
                setOpaque(true);
                setBackground(color);
            }
        };
        return label;
    }
}
