package com.petercoulton.gosgt.auctionsniper;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

import java.util.*;

public class AuctionMessageTranslator implements MessageListener {

    private final IAuctionEventListener listener;

    public AuctionMessageTranslator(IAuctionEventListener listener) {
        this.listener = listener;
    }

    @Override
    public void processMessage(Chat chat, Message message) {
        Map<String, String> event = new HashMap<>();
        for (String element : message.getBody().split(";")) {
            String[] pair = element.split(":");
            event.put(pair[0].trim(), pair[1].trim());
        }

        String type = event.get("Event");
        if ("CLOSE".equalsIgnoreCase(type)) {
            listener.auctionClosed();
        } else {
            listener.currentPrice(
                    Integer.parseInt(event.get("CurrentPrice")),
                    Integer.parseInt(event.get("Increment")));
        }


    }


}
