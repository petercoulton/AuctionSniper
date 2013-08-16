package com.petercoulton.gosgt.auctionsniper;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

import java.util.HashMap;
import java.util.Map;

import static com.petercoulton.gosgt.auctionsniper.IAuctionEventListener.PriceSource;

public class AuctionMessageTranslator implements MessageListener {

    private final String sniperID;
    private final IAuctionEventListener listener;

    public AuctionMessageTranslator(String sniperID, IAuctionEventListener listener) {
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

        public String getString(String field) {
            return fields.get(field);
        }

        public PriceSource isFrom(String sniperID) {
            return sniperID.equals(bidder()) ? PriceSource.FromSniper : PriceSource.FromOtherBidder;
        }

        public String bidder() {
            return fields.get("Bidder");
        }
    }
}
