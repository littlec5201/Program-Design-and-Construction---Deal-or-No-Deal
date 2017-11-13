
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class PrizeMoneyLabels {

    PrizeMoney prizeMoney = new PrizeMoney();

    public static JLabel westLabel[] = new JLabel[13];
    JPanel panelWest = new JPanel();
    static JPanel westContainerPanel = new JPanel();

    public static JLabel eastLabel[] = new JLabel[13];
    JPanel panelEast = new JPanel();
    static JPanel eastContainerPanel = new JPanel();

    public PrizeMoneyLabels() {
        westPanel();
        eastPanel();
    }

    /**
     * 
     * @param i the index of a westLabel
     * @param color the color the westLabel at index i will be changed to
     * @return the new JLabel with an updated color
     */
    public JLabel colorWest(int i, Color color) {
        westLabel[i] = new JLabel("$" + prizeMoney.getInitialPrizeMoneyValue(i), SwingConstants.CENTER) {
            {
                setSize(prizeMoney.x, prizeMoney.y);
                setPreferredSize(getSize());
                setMinimumSize(getSize());
                setMaximumSize(getSize());
                setFont(prizeMoney.font);
                setOpaque(true);
                setBackground(color);
                setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
        };
        return westLabel[i];
    }

    /**
     * 
     * @param i the index of a eastLabel
     * @param color the color the eastLabel at index i will be changed to
     * @return the new JLabel with an updated color
     */
    public JLabel colorEast(int i, Color color) {
        eastLabel[i] = new JLabel("$" + prizeMoney.getInitialPrizeMoneyValue(i + 13), SwingConstants.CENTER) {
            {
                setSize(prizeMoney.x, prizeMoney.y);
                setPreferredSize(getSize());
                setMinimumSize(getSize());
                setMaximumSize(getSize());
                setFont(prizeMoney.font);
                setOpaque(true);
                setBackground(color);
                setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
        };
        return eastLabel[i];
    }

    /**
     * The panel containing the lowest 13 values in the game
     */
    public void westPanel() {
        westContainerPanel.setBorder(new EmptyBorder(5, 5, 0, 0));
        westContainerPanel.setLayout(new BorderLayout());
        panelWest.setLayout(new GridLayout(13, 1, 15, 5));

        for (int i = 0; i < 13; i++) {
            JLabel west = westLabel[i];
            if (prizeMoney.newPrizeMoneyArray[i] != 0) {
                west = colorWest(i, new Color(255, 215, 0));
            }
            panelWest.add(west);
        }
        westContainerPanel.add(panelWest);
    }

    /**
     * The panel containing the highest 13 values in the game
     */
    public void eastPanel() {
        eastContainerPanel.setBorder(new EmptyBorder(5, 0, 0, 5));
        eastContainerPanel.setLayout(new BorderLayout());
        panelEast.setLayout(new GridLayout(13, 1, 15, 5));

        for (int i = 0; i < 13; i++) {
            JLabel east = eastLabel[i];
            if (prizeMoney.getInitialPrizeMoneyValue(i) != 0) {
                east = colorEast(i, new Color(255, 215, 0));
            }
            panelEast.add(east);
        }
        eastContainerPanel.add(panelEast);
    }
}
