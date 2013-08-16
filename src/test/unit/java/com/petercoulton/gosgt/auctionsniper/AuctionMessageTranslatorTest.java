package com.petercoulton.gosgt.auctionsniper;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

public class AuctionMessageTranslatorTest {

    public static final Chat UNUSED_CHAT = null;

    @Test public void
    notifies_listener_that_auction_is_closed_when_auction_closed_message_received() throws Exception {
        // Arrange
        IAuctionEventListener listener = mock(IAuctionEventListener.class);
        AuctionMessageTranslator translator = new AuctionMessageTranslator(listener);

        Message message = new Message();
        message.setBody("SOLVersion: 1.1; Event: CLOSE;");

        // Act
        translator.processMessage(UNUSED_CHAT, message);

        // Assert
        verify(listener, only()).auctionClosed();
    }

    @Test public void
    passes_bid_details_to_listeners_when_price_message_received() throws Exception {
        // Arrange
        IAuctionEventListener listener = mock(IAuctionEventListener.class);
        AuctionMessageTranslator translator = new AuctionMessageTranslator(listener);

        Message message = new Message();
        message.setBody("SOLVersion: 1.1; Event: Price; CurrentPrice: 147; Increment: 11; Bidder: Bob Smith;");

        // Act
        translator.processMessage(UNUSED_CHAT, message);

        // Assert
        verify(listener).currentPrice(147, 11);
    }
}
