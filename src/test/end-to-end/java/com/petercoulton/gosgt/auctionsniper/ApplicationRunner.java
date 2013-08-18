package com.petercoulton.gosgt.auctionsniper;

import static com.petercoulton.gosgt.auctionsniper.FakeAuctionServer.XMPP_HOSTNAME;

public class ApplicationRunner {
    public static final String SNIPER_USERNAME = "sniper";
    public static final String SNIPER_PASSWORD = "sniper";
    public static final String SNIPER_XMPP_ID = SNIPER_USERNAME + "@" + XMPP_HOSTNAME + "/Auction";;

    private AuctionSniperDriver driver;

    public void startBiddingIn(final FakeAuctionServer... auctions) {
        Thread thread = new Thread("Test Application") {
            @Override
            public void run() {
                try {
                    Main.main(arguments(auctions));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
        driver = new AuctionSniperDriver(1000);
        driver.hasTitle(MainWindow.APPLICATION_TITLE);
        driver.hasColumnTitles();
        for (FakeAuctionServer auction : auctions) {
            driver.showsSniperStatus("", 0, 0, MainWindow.STATUS_JOINING);
        }
    }

    private String[] arguments(final FakeAuctionServer[] auctions) {
        String[] arguments = new String[3 + auctions.length];
        arguments[0] = XMPP_HOSTNAME;
        arguments[1] = SNIPER_USERNAME;
        arguments[2] = SNIPER_PASSWORD;
        for (int i=0; i<auctions.length; i++) {
            arguments[3 + i] = auctions[i].getItemID();
        }
        return arguments;
    }

    public void hasShownSniperIsBidding(final FakeAuctionServer auction, int lastPrice, int lastBid) {
        driver.showsSniperStatus(auction.getItemID(), lastPrice, lastBid, MainWindow.STATUS_BIDDING);
    }

    public void hasShownSniperIsWinning(final FakeAuctionServer auction, int winningBid) {
        driver.showsSniperStatus(auction.getItemID(), winningBid, winningBid, MainWindow.STATUS_WINNING);
    }

    public void showsSniperHasWonAuction(final FakeAuctionServer auction, int lastPrice) {
        driver.showsSniperStatus(auction.getItemID(), lastPrice, lastPrice, MainWindow.STATUS_WON);
    }

    public void showsSniperHasLostAuction(final FakeAuctionServer auction) {
        driver.showsSniperStatus(MainWindow.STATUS_LOST);
    }

    public void stop() {
        if (driver != null) {
            driver.dispose();
        }
    }
}
