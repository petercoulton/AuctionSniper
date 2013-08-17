package com.petercoulton.gosgt.auctionsniper;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

public class AuctionMessageTranslator implements MessageListener {

    private final String sniperID;
    private final AuctionEventListener listener;

    public AuctionMessageTranslator(String sniperID, AuctionEventListener listener) {
        this.sniperID = sniperID;
        this.listener = listener;
    }

    @Override
    public void processMessage(Chat chat, Message message) {
        final AuctionEvent event = AuctionEvent.from(message.getBody());

        if ("CLOSE".equalsIgnoreCase(event.getType())) {
            listener.auctionClosed();
        } else {
            listener.currentPrice(
                    event.getInt("CurrentPrice"),
                    event.getInt("Increment"),
                    event.isFrom(sniperID));
        }
    }

}
