package com.petercoulton.gosgt.auctionsniper;


import org.junit.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AuctionSniperTest {
    @Test public void
    should_report_that_it_lost_when_an_auction_closes() throws Exception {
        // Arrange
        ISniperListener listener = mock(ISniperListener.class);
        Auction UNUSED_AUCTION = mock(Auction.class);
        AuctionSniper sniper = new AuctionSniper(UNUSED_AUCTION, listener);

        // Act
        sniper.auctionClosed();

        // Assert
        verify(listener).sniperLost();
    }

    @Test
    public void
    should_bid_higher_and_notify_listeners_whenever_a_new_price_arrives() throws Exception {
        // Arrange
        final int price = 1001;
        final int increment = 25;

        ISniperListener listener = mock(ISniperListener.class);
        Auction auction = mock(Auction.class);
        AuctionSniper sniper = new AuctionSniper(auction, listener);

        // Act
        sniper.currentPrice(price, increment);

        // Assert
        verify(auction).bid(price + increment);
        verify(listener).sniperBidding();
    }
}
