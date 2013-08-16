package com.petercoulton.gosgt.auctionsniper;


public class AuctionSniper implements IAuctionEventListener {
    private final Auction auction;
    private final ISniperListener listener;

    public AuctionSniper(Auction auction, ISniperListener listener) {
        this.auction = auction;
        this.listener = listener;
    }

    @Override
    public void auctionClosed() {
        listener.sniperLost();
    }

    @Override
    public void currentPrice(int price, int increment) {
        listener.sniperBidding();
        auction.bid(price + increment);
    }
}
