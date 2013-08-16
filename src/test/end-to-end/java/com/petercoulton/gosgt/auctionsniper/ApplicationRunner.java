package com.petercoulton.gosgt.auctionsniper;

import static com.petercoulton.gosgt.auctionsniper.FakeAuctionServer.XMPP_HOSTNAME;

public class ApplicationRunner {
    public static final String SNIPER_USERNAME = "sniper";
    public static final String SNIPER_PASSWORD = "sniper";
    public static final String SNIPER_XMPP_ID = SNIPER_USERNAME + "@" + XMPP_HOSTNAME + "/Auction";;

    AuctionSniperDriver driver;

    public void startBiddingIn(final FakeAuctionServer auction) {
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

    public void showsSniperHasLostAuction() {
        driver.showsSniperStatus(MainWindow.STATUS_LOST);
    }

    public void hasShownSniperItBidding() {
        driver.showsSniperStatus(MainWindow.STATUS_BIDDING);
    }

    public void stop() {
        if (driver != null) {
            driver.dispose();
        }
    }
}
