
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class GUI {

    public JFrame frame = new JFrame("Deal or No Deal");
    public static JPanel textPanel = new JPanel();
    public static JLabel textLabel = new JLabel("", SwingConstants.CENTER);
    Main dond = new Main();

    public GUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(dond);
        frame.add(PrizeMoneyLabels.westContainerPanel, BorderLayout.WEST);
        frame.add(dond.dealPanel, BorderLayout.SOUTH);
        frame.add(PrizeMoneyLabels.eastContainerPanel, BorderLayout.EAST);
        frame.add(GUI.textPanel, BorderLayout.NORTH);
        frame.add(dond.briefCaseMap.getBriefCasePanel(), BorderLayout.CENTER);
        frame.pack();
        frame.setSize(new Dimension(1050, 700));
        frame.setResizable(false);
        frame.setVisible(true);
    }

    /**\
     * 
     * @param text changes the text at the top of the game
     * @return a new JLabel with the updated text
     */
    public static JLabel textLabel(String text) {
        textLabel = new JLabel(text, SwingConstants.CENTER) {
            {
                setSize(1000, 50);
                setPreferredSize(getSize());
                setMinimumSize(getSize());
                setMaximumSize(getSize());
            }
        };
        return textLabel;
    }
}
