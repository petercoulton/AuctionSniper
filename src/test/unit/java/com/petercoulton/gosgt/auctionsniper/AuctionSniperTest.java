package com.petercoulton.gosgt.auctionsniper;


import org.junit.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AuctionSniperTest {
    @Test public void
    should_report_that_it_lost_when_an_auction_closes() throws Exception {
        // Arrange
        ISniperListener listener = mock(ISniperListener.class);
        AuctionSniper sniper = new AuctionSniper(listener);

        // Act
        sniper.auctionClosed();

        // Assert
        verify(listener).sniperLost();
    }
}
