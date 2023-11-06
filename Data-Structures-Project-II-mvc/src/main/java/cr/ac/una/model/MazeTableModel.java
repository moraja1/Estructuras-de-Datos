package cr.ac.una.model;

import javax.swing.table.AbstractTableModel;

public class MazeTableModel extends AbstractTableModel {
    private String[] columnNames = {"Nombre", "Fechas de Creación", "Filas", "Columnas"};
    private Object[][] data = {{"Juan", "Pérez", 25, 80}, {"María", "García", 30, 65}, {"Pedro", "López", 35, 56}};

    public MazeTableModel() {

    }

    public MazeTableModel(String[] columnNames, Object[][] data) {
        this.columnNames = columnNames;
        this.data = data;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }
}
