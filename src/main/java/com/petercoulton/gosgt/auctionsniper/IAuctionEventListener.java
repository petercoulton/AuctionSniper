package com.petercoulton.gosgt.auctionsniper;

public interface IAuctionEventListener {
    void auctionClosed();

    void currentPrice(int price, int increment);
}
