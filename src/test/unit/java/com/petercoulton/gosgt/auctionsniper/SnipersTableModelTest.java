package com.petercoulton.gosgt.auctionsniper;

import org.junit.Test;

import static com.petercoulton.gosgt.auctionsniper.SnipersTableModel.Column;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SnipersTableModelTest {
    @Test public void
    should_have_enough_columns_to_display_sniper_status() throws Exception {
        assertThat(new SnipersTableModel().getColumnCount(), is(Column.values().length));
    }

    @Test
    public void
    should_update_columns_when_sniper_status_update_received() throws Exception {
        // Arrange
        final SnipersTableModel model = new SnipersTableModel();

        // Act
        model.sniperStatusChanged(new SniperState("item-12345", 132, 12), MainWindow.STATUS_BIDDING);

        // Assert
        assertThat(model.getValueAt(0, Column.ITEM_IDENTIFIER.ordinal()), is((Object)"item-12345"));
        assertThat(model.getValueAt(0, Column.LAST_PRICE.ordinal()), is((Object)132));
        assertThat(model.getValueAt(0, Column.LAST_BID.ordinal()), is((Object)12));
        assertThat(model.getValueAt(0, Column.SNIPER_STATUS.ordinal()), is((Object)MainWindow.STATUS_BIDDING));
    }
}
