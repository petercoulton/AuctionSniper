package com.petercoulton.gosgt.auctionsniper;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SniperSnapshot {
    public final String itemID;
    public final int price;
    public final int bid;
    public final SniperState state;

    public SniperSnapshot(String itemID, int price, int bid, SniperState state) {
        this.itemID = itemID;
        this.price = price;
        this.bid = bid;
        this.state = state;
    }

    public static SniperSnapshot joining(String itemID) {
        return new SniperSnapshot(itemID, 0, 0, SniperState.JOINING);
    }

    public SniperSnapshot winning(int price) {
        return new SniperSnapshot(itemID, price, bid, SniperState.WINNING);
    }

    public SniperSnapshot bidding(int price, int bid) {
        return new SniperSnapshot(itemID, price, bid, SniperState.BIDDING);
    }

    public SniperSnapshot closed() {
        return new SniperSnapshot(itemID, price, bid, state.whenAuctionClosed());
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
