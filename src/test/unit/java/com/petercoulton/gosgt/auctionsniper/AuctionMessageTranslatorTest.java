package com.petercoulton.gosgt.auctionsniper;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AuctionMessageTranslatorTest {

    public static final Chat UNUSED_CHAT = null;

    @Test
    public void
    notifies_listener_when_auction_closed_message_received() throws Exception {
        // Arrange
        IAuctionEventListener listener = mock(IAuctionEventListener.class);
        AuctionMessageTranslator translator = new AuctionMessageTranslator(listener);

        // Act
        translator.processMessage(UNUSED_CHAT, new Message());

        // Assert
        verify(listener).auctionClosed();
    }
}
