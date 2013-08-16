package com.petercoulton.gosgt.auctionsniper;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class MainWindow extends JFrame {
    public static final String STATUS_JOINING = "Joining";
    public static final String STATUS_BIDDING = "Bidding";
    public static final String STATUS_LOST    = "Lost";

    public static final String MAIN_WINDOW_TITLE = "Auction Sniper";
    public static final String MAIN_WINDOW_NAME = "Auction Sniper";

    public static final String SNIPER_STATUS_NAME = "Sniper Status";

    private final JLabel sniperStatus = createLabel(STATUS_JOINING);

    public MainWindow() {
        super(MAIN_WINDOW_TITLE);
        setName(MAIN_WINDOW_NAME);
        add(sniperStatus);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private static JLabel createLabel(String initialText) {
        JLabel label = new JLabel(initialText);
        label.setName(SNIPER_STATUS_NAME);
        label.setBorder(new LineBorder(Color.BLACK));
        return label;
    }

    public void showStatus(String status) {
        sniperStatus.setText(status);
    }
}
