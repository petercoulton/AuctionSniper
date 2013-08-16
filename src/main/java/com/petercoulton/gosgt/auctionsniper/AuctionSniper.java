package com.petercoulton.gosgt.auctionsniper;


public class AuctionSniper implements IAuctionEventListener {
    private final ISniperListener listener;

    public AuctionSniper(ISniperListener listener) {
        this.listener = listener;
    }

    @Override
    public void auctionClosed() {
        listener.sniperLost();
    }

    @Override
    public void currentPrice(int price, int increment) {
    }
}
