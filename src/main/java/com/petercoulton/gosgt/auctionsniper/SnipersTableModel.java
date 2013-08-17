package com.petercoulton.gosgt.auctionsniper;

import javax.swing.table.AbstractTableModel;

public class SnipersTableModel extends AbstractTableModel {
    private static final SniperSnapshot STARTING_UP = new SniperSnapshot("", 0, 0, SniperState.JOINING);
    private String state = MainWindow.STATUS_JOINING;
    private SniperSnapshot snapshot = STARTING_UP;

    private static String[] STATUS_TEXT = {
            "Joining", "Bidding", "Winning", "Lost", "Won"
    };

    public enum Column {
        ITEM_IDENTIFIER,
        LAST_PRICE,
        LAST_BID,
        SNIPER_STATUS;

        public static Column at(int offset) {
            return values()[offset];
        }


    }

    @Override
    public int getRowCount() {
        return 1;
    }

    @Override
    public int getColumnCount() {
        return Column.values().length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (Column.at(columnIndex)) {
            case ITEM_IDENTIFIER:
                return snapshot.itemID;
            case LAST_PRICE:
                return snapshot.price;
            case LAST_BID:
                return snapshot.bid;
            case SNIPER_STATUS:
                return textFor(snapshot.state);
            default:
                throw new IllegalArgumentException("No column at " + columnIndex);
        }
    }

    private String textFor(SniperState state) {
        return STATUS_TEXT[state.ordinal()];
    }

    public void sniperStatusChanged(SniperSnapshot snapshot) {
        this.snapshot = snapshot;
        fireTableRowsUpdated(0, 0);
    }
}
