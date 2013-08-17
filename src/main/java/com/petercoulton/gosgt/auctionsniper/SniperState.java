package com.petercoulton.gosgt.auctionsniper;

public class SniperState {
    private final String itemID;
    private final int price;
    private final int bid;

    public SniperState(String itemID, int price, int bid) {
        this.itemID = itemID;
        this.price = price;
        this.bid = bid;
    }

    public String getItemID() {
        return itemID;
    }

    public int getPrice() {
        return price;
    }

    public int getBid() {
        return bid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SniperState that = (SniperState) o;

        if (bid != that.bid) return false;
        if (price != that.price) return false;
        if (!itemID.equals(that.itemID)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = itemID.hashCode();
        result = 31 * result + price;
        result = 31 * result + bid;
        return result;
    }
}
