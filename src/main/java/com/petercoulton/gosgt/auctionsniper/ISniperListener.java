package com.petercoulton.gosgt.auctionsniper;

public interface ISniperListener {
    void sniperLost();
    void sniperBidding(SniperState sniperState);
    void sniperWinning();
    void sniperWon();
}
