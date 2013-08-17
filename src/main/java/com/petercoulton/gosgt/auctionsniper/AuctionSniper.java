package com.petercoulton.gosgt.auctionsniper;


public class AuctionSniper implements AuctionEventListener {
    private final Auction auction;
    private final SniperListener listener;

    private SniperSnapshot snapshot;

    public AuctionSniper(String itemID, Auction auction, SniperListener listener) {
        this.auction = auction;
        this.listener = listener;
        this.snapshot = SniperSnapshot.joining(itemID);
    }

    @Override
    public void auctionClosed() {
        snapshot = snapshot.closed();
        notifyChange();
    }

    @Override
    public void currentPrice(int price, int increment, PriceSource priceSource) {
        switch (priceSource) {
            case FromSniper:
                snapshot = snapshot.winning(price);
                break;
            case FromOtherBidder:
                final int bid = price + increment;
                auction.bid(bid);
                snapshot = snapshot.bidding(price, bid);
                break;
        }
        notifyChange();
    }

    private void notifyChange() {
        listener.sniperStateChanged(snapshot);
    }
}
