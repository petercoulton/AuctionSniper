package com.petercoulton.gosgt.auctionsniper;

public interface ISniperListener {
    void sniperLost();
    void sniperBidding();
    void sniperWinning();
    void sniperWon();
}
