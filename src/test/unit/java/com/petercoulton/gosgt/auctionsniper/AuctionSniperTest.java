package com.petercoulton.gosgt.auctionsniper;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AuctionSniperTest {

    @Mock private ISniperListener listener;
    @Mock private Auction auction;

    private AuctionSniper sniper;

    @Before
    public void setUp() throws Exception {
        sniper = new AuctionSniper(auction, listener);
    }

    @Test public void
    should_report_that_it_lost_when_an_auction_closes() throws Exception {
        // Arrange
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

        // Act
        sniper.currentPrice(price, increment);

        // Assert
        verify(auction).bid(price + increment);
        verify(listener).sniperBidding();
    }
}
