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
        final AuctionEvent event = AuctionEvent.from(message.getBody());

        if ("CLOSE".equalsIgnoreCase(event.getType())) {
            listener.auctionClosed();
        } else {
            listener.currentPrice(event.getInt("CurrentPrice"), event.getInt("Increment"));
        }
    }

    private static class AuctionEvent {
        private final Map<String, String> fields = new HashMap<>();

        private void addField(String field) {
            String[] pair = field.split(":");
            fields.put(pair[0].trim(), pair[1].trim());
        }

        static AuctionEvent from(String messageBody) {
            AuctionEvent event = new AuctionEvent();
            for (String field : fieldsIn(messageBody)) {
                event.addField(field);
            }
            return event;
        }

        static String[] fieldsIn(String messageBody) {
            return messageBody.split(";");
        }

        public String getType() {
            return fields.get("Event");
        }

        public int getInt(String field) {
            return Integer.valueOf(fields.get(field));
        }
    }
}
