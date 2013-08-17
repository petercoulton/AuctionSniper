package com.petercoulton.gosgt.auctionsniper;

import java.util.HashMap;
import java.util.Map;

class AuctionEvent {
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

    public AuctionEventListener.PriceSource isFrom(String sniperID) {
        return sniperID.equals(bidder()) ? AuctionEventListener.PriceSource.FromSniper : AuctionEventListener.PriceSource.FromOtherBidder;
    }

    public String bidder() {
        return fields.get("Bidder");
    }
}
