package com.petercoulton.gosgt.auctionsniper;

import com.objogate.wl.swing.AWTEventQueueProber;
import com.objogate.wl.swing.driver.JFrameDriver;
import com.objogate.wl.swing.driver.JTableDriver;
import com.objogate.wl.swing.driver.JTableHeaderDriver;
import com.objogate.wl.swing.gesture.GesturePerformer;

import javax.swing.table.JTableHeader;

import static com.objogate.wl.swing.matcher.IterableComponentsMatcher.matching;
import static com.objogate.wl.swing.matcher.JLabelTextMatcher.withLabelText;

public class AuctionSniperDriver extends JFrameDriver {

    public AuctionSniperDriver(int timeoutMillis) {
        super(new GesturePerformer(),
              JFrameDriver.topLevelFrame(
                      named(MainWindow.MAIN_WINDOW_NAME),
                      showingOnScreen()),
              new AWTEventQueueProber(timeoutMillis, 100)
        );
    }

    public void showsSniperStatus(String status) {
        new JTableDriver(this).hasCell(withLabelText(status));
    }

    public void showsSniperStatus(String itemID, int lastPrice, int lastBid, String status) {
        final JTableDriver table = new JTableDriver(this);
        table.hasRow(
                matching(withLabelText(itemID), withLabelText(String.valueOf(lastPrice)),
                        withLabelText(String.valueOf(lastBid)), withLabelText(status))
        );
    }

    public void hasColumnTitles() {
        JTableHeaderDriver headers = new JTableHeaderDriver(this, JTableHeader.class);
        headers.hasHeaders(matching(
                withLabelText("Item"), withLabelText("Last Price"),
                withLabelText("Last Bid"), withLabelText("State")
        ));
    }
}
