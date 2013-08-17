package com.petercoulton.gosgt.auctionsniper;

import static com.petercoulton.gosgt.auctionsniper.FakeAuctionServer.XMPP_HOSTNAME;

public class ApplicationRunner {
    public static final String SNIPER_USERNAME = "sniper";
    public static final String SNIPER_PASSWORD = "sniper";
    public static final String SNIPER_XMPP_ID = SNIPER_USERNAME + "@" + XMPP_HOSTNAME + "/Auction";;

    private AuctionSniperDriver driver;
    private String itemID;

    public void startBiddingIn(final FakeAuctionServer auction) {
        itemID = auction.getItemID();
        Thread thread = new Thread("Test Application") {
            @Override
            public void run() {
                try {
                    Main.main(XMPP_HOSTNAME, SNIPER_USERNAME, SNIPER_PASSWORD, auction.getItemID());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
        driver = new AuctionSniperDriver(1000);
        driver.showsSniperStatus(MainWindow.STATUS_JOINING);
    }

    public void hasShownSniperItBidding(int lastPrice, int lastBid) {
        driver.showsSniperStatus(itemID, lastPrice, lastBid, MainWindow.STATUS_BIDDING);
    }

    public void hasShownSniperIsWinning(int winningBid) {
        driver.showsSniperStatus(itemID, winningBid, winningBid, MainWindow.STATUS_WINNING);
    }

    public void showsSniperHasWonAuction(int lastPrice) {
        driver.showsSniperStatus(itemID, lastPrice, lastPrice, MainWindow.STATUS_WON);
    }

    public void showsSniperHasLostAuction() {
        driver.showsSniperStatus(MainWindow.STATUS_LOST);
    }

    public void stop() {
        if (driver != null) {
            driver.dispose();
        }
    }
}
