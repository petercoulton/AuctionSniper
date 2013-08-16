package com.petercoulton.gosgt.auctionsniper;

import javax.swing.table.AbstractTableModel;

public class SnipersTableModel extends AbstractTableModel {
    private String statusText = MainWindow.STATUS_JOINING;

    public void setStatusText(String status) {
        statusText = status;
        fireTableRowsUpdated(0, 0);
    }

    @Override
    public int getRowCount() {
        return 1;
    }

    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return statusText;
    }
}
