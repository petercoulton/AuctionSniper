package com.petercoulton.gosgt.auctionsniper;


public class AuctionSniper implements IAuctionEventListener {
    private boolean isWinning = false;

    private final String itemID;
    private final Auction auction;
    private final ISniperListener listener;

    public AuctionSniper(String itemID, Auction auction, ISniperListener listener) {
        this.itemID = itemID;
        this.auction = auction;
        this.listener = listener;
    }

    @Override
    public void auctionClosed() {
        if (isWinning) {
            listener.sniperWon();
        } else {
            listener.sniperLost();
        }
    }

    @Override
    public void currentPrice(int price, int increment, PriceSource priceSource) {
        isWinning = priceSource == PriceSource.FromSniper;
        if (isWinning) {
            listener.sniperWinning();
        } else {
            final int bid = price + increment;
            auction.bid(bid);
            listener.sniperBidding(new SniperState(itemID, price, bid));
        }
    }
}
