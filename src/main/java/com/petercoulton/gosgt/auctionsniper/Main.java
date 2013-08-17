package com.petercoulton.gosgt.auctionsniper;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;

import static java.lang.String.format;

public class Main {
    private static final int ARG_HOSTNAME = 0;
    private static final int ARG_USERNAME = 1;
    private static final int ARG_PASSWORD = 2;
    private static final int ARG_ITEM_ID  = 3;

    public static final String AUCTION_RESOURCE = "Auction";
    public static final String ACTION_ID_FORMAT = "auction-%s@%s/" + AUCTION_RESOURCE;
    public static final String SNIPER_ID = "sniper";

    public static final String BID_COMMAND_FORMAT  = "SOLVersion: 1.1; Command: BID; Price: %d;";
    public static final String JOIN_COMMAND_FORMAT  = "SOLVersion: 1.1; Command: JOIN";

    public MainWindow ui;

    @SuppressWarnings({"UnusedDeclaration", "FieldCanBeLocal"})
    private Chat notToBeGCd;

    public Main() throws InvocationTargetException, InterruptedException {
        startUserInterface();
    }

    public static void main(String... args) throws InvocationTargetException, InterruptedException, XMPPException {
        Main main = new Main();
        String userID = args[ARG_USERNAME];
        main.joinAuction(
                connectTo(args[ARG_HOSTNAME], userID, args[ARG_PASSWORD]),
                args[ARG_ITEM_ID]);
    }

    private void joinAuction(final XMPPConnection connection, String itemID) throws XMPPException {
        disconnectWhenUICloses(connection);

        final Chat chat = connection.getChatManager().createChat(auctionID(itemID, connection), null);
        this.notToBeGCd = chat;

        Auction auction = new XMPPAuction(chat);

        chat.addMessageListener(new AuctionMessageTranslator(
                connection.getUser(),
                new AuctionSniper(itemID, auction, new SniperStatusDisplayer())));

        auction.join();
    }

    private void disconnectWhenUICloses(final XMPPConnection connection) {
        ui.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                connection.disconnect();
            }
        });
    }

    private static String auctionID(String itemID, XMPPConnection connection) {
        return format(ACTION_ID_FORMAT, itemID, connection.getServiceName());
    }

    private static XMPPConnection connectTo(String hostname, String username, String password) throws XMPPException {
        XMPPConnection connection = new XMPPConnection(hostname);
        connection.connect();
        connection.login(username, password, AUCTION_RESOURCE);
        return connection;
    }

    private void startUserInterface() throws InterruptedException, InvocationTargetException {
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                ui = new MainWindow();
            }
        });
    }

    public class SniperStatusDisplayer implements ISniperListener {
        @Override
        public void sniperBidding(final SniperState sniperState) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    ui.sniperStateChanged(sniperState, MainWindow.STATUS_BIDDING);
                }
            });
        }

        @Override
        public void sniperWinning() {
            showStatus(MainWindow.STATUS_WINNING);
        }

        @Override
        public void sniperWon() {
            showStatus(MainWindow.STATUS_WON);
        }

        @Override
        public void sniperLost() {
            showStatus(MainWindow.STATUS_LOST);
        }

        private void showStatus(final String status) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    ui.showStatus(status);
                }
            });
        }
    }
}
