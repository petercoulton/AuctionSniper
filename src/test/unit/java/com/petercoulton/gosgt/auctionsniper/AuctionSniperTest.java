package com.petercoulton.gosgt.auctionsniper;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.petercoulton.gosgt.auctionsniper.IAuctionEventListener.PriceSource;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AuctionSniperTest {

    public static final String ITEM_ID = "item-54321";
    @Mock private ISniperListener listener;
    @Mock private Auction auction;

    private AuctionSniper sniper;

    @Before
    public void setUp() throws Exception {
        sniper = new AuctionSniper(ITEM_ID, auction, listener);
    }

    @Test public void
    should_report_that_it_lost_when_an_auction_closes_before_bidding() throws Exception {
        // Arrange
        // Act
        sniper.auctionClosed();

        // Assert
        verify(listener).sniperLost();
    }

    @Test public void
    should_report_that_it_lost_when_an_auction_closes_while_bidding() throws Exception {
        // Arrange
        final int price = 1001;
        final int increment = 25;
        final int bid = price + increment;

        // Act
        sniper.currentPrice(price, increment, PriceSource.FromOtherBidder);
        sniper.auctionClosed();

        // Assert
        verify(auction).bid(bid);
        verify(listener).sniperBidding(new SniperState(ITEM_ID, price, bid));
        verify(listener).sniperLost();
    }

    @Test public void
    should_bid_higher_and_notify_listeners_whenever_a_new_price_arrives() throws Exception {
        // Arrange
        final int price = 1001;
        final int increment = 25;
        final int bid = price + increment;

        // Act
        sniper.currentPrice(price, increment, PriceSource.FromOtherBidder);

        // Assert
        verify(auction).bid(bid);
        verify(listener).sniperBidding(new SniperState(ITEM_ID, price, bid));
    }

    @Test public void
    should_report_sniper_is_winning_when_current_price_comes_from_sniper() throws Exception {
        // Arrange
        final int price = 1001;
        final int increment = 25;

        // Act
        sniper.currentPrice(price, increment, PriceSource.FromSniper);

        // Assert
        verify(listener).sniperWinning();
    }

    @Test public void
    should_report_sniper_won_when_auction_closes_while_sniper_is_winning() throws Exception {
        // Arrange
        // Act
        sniper.currentPrice(1001, 25, PriceSource.FromSniper);
        sniper.auctionClosed();

        // Assert
        verify(listener).sniperWinning();
        verify(listener).sniperWon();
    }
}
