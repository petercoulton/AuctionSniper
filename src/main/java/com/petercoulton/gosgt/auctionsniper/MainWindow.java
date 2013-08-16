package com.petercoulton.gosgt.auctionsniper;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    public static final String STATUS_JOINING = "Joining";
    public static final String STATUS_BIDDING = "Bidding";
    public static final String STATUS_LOST    = "Lost";
    public static final String STATUS_WINNING = "Winning";
    public static final String STATUS_WON     = "Won";

    public static final String MAIN_WINDOW_TITLE = "Auction Sniper";
    public static final String MAIN_WINDOW_NAME = "Auction Sniper";

    public static final String SNIPERS_TABLE_NAME = "Sniper Status";

    private SnipersTableModel snipers;

    public MainWindow() {
        super(MAIN_WINDOW_TITLE);
        setName(MAIN_WINDOW_NAME);
        fillContentPane(makeSnipersTable());
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void fillContentPane(JTable snipersTable) {
        final Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(new JScrollPane(snipersTable), BorderLayout.NORTH);
    }

    private JTable makeSnipersTable() {
        snipers = new SnipersTableModel();
        JTable snipersTable = new JTable(snipers);
        snipersTable.setName(SNIPERS_TABLE_NAME);
        return snipersTable;
    }

    public void showStatus(String status) {
        snipers.setStatusText(status);
    }
}
