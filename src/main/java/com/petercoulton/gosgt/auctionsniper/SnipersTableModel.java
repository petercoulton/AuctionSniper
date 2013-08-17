package com.petercoulton.gosgt.auctionsniper;

import javax.swing.table.AbstractTableModel;

public class SnipersTableModel extends AbstractTableModel {
    private static final SniperState STARTING_UP = new SniperState("", 0, 0);
    private String statusText = MainWindow.STATUS_JOINING;
    private SniperState sniperState = STARTING_UP;

    public enum Column {
        ITEM_IDENTIFIER, LAST_PRICE, LAST_BID, SNIPER_STATUS;

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
                return sniperState.getItemID();
            case LAST_PRICE:
                return sniperState.getPrice();
            case LAST_BID:
                return sniperState.getBid();
            case SNIPER_STATUS:
                return statusText;
        }

        return statusText;
    }

    public void sniperStatusChanged(SniperState state, String statusText) {
        sniperState = state;
        this.statusText = statusText;
        fireTableRowsUpdated(0, 0);
    }

    public void setStatusText(String status) {
        statusText = status;
        fireTableRowsUpdated(0, 0);
    }
}
