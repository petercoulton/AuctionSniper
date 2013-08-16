package com.petercoulton.gosgt.auctionsniper;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static java.lang.String.format;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AuctionMessageTranslatorTest {

    public static final Chat UNUSED_CHAT = null;

    @Mock private IAuctionEventListener listener;

    private AuctionMessageTranslator translator;

    @Before
    public void setUp() throws Exception {
        translator = new AuctionMessageTranslator(listener);
    }

    @Test public void
    notifies_listener_that_auction_is_closed_when_auction_closed_message_received() throws Exception {
        // Arrange
        // Act
        translator.processMessage(UNUSED_CHAT, auctionClosedMessage());

        // Assert
        verify(listener, only()).auctionClosed();
    }

    @Test public void
    passes_bid_details_to_listeners_when_price_message_received() throws Exception {
        // Arrange
        final int price = 147;
        final int increment = 11;

        // Act
        translator.processMessage(UNUSED_CHAT, priceMessage(price, increment));

        // Assert
        verify(listener).currentPrice(price, increment);
    }

    private Message priceMessage(final int price, final int increment) {
        return new Message() {{
            setBody(format("SOLVersion: 1.1; Event: Price; CurrentPrice: %d; Increment: %d; Bidder: Paul Anka;",
                    price, increment));
        }};
    }

    private Message auctionClosedMessage() {
        return new Message() {{
            setBody("SOLVersion: 1.1; Event: CLOSE;");
        }};
    }
}
