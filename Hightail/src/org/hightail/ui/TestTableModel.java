package org.hightail.ui;

import javax.swing.table.AbstractTableModel;
import org.hightail.TestcaseSet;

class TestTableModel extends AbstractTableModel {
    
    protected TestcaseSet testcaseSet;
    protected final String[] columnNames = {"Input", "Expected output", "Program output", "Result"};
    protected final Class[] columnClasses = {String.class, String.class, String.class, String.class}; // TODO: custom shit
    
    public TestTableModel(TestcaseSet testcaseSet) {
        this.testcaseSet = testcaseSet;
    }
    
    @Override
    public int getRowCount() {
        return testcaseSet.size();
    }
    
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClasses[columnIndex];
    }
    
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return testcaseSet.get(rowIndex).getInput();
            case 1:
                return testcaseSet.get(rowIndex).getExpectedOutput();
            case 2:
                return testcaseSet.get(rowIndex).getProgramOutput();
            case 3:
                return testcaseSet.get(rowIndex).getExecutionResult().toString();
            default:
                throw new UnsupportedOperationException("Implementation error: invalid columnIndex.");
        }
    }
    
    public void rowUpdated (int index) {
        fireTableRowsUpdated(index, index);
    }
    
    public void rowInserted () {
        fireTableRowsInserted(getRowCount()-1, getRowCount()-1);
    }
    
    public void rowDeleted (int index) {
        fireTableRowsDeleted(index, index);
    }
    
    public void setTemporaryIndexesForTestcases() {
        for (int rowIndex = 0; rowIndex < getRowCount(); ++rowIndex) {
            testcaseSet.get(rowIndex).setIndex(rowIndex);
        }
    }
    
}
