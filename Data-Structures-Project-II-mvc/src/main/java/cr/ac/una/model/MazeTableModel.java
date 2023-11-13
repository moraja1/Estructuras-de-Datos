package cr.ac.una.model;

import cr.ac.una.util.graphs.MGraph;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class MazeTableModel extends AbstractTableModel {
    private final HashMap<String, MGraph> mazes;
    private Class[] classes = {String.class, LocalDate.class, Integer.class, Integer.class};
    private String[] columnNames = {"Nombre", "Fechas de Creación", "Filas", "Columnas"};
    private Object[][] data;

    public MazeTableModel() {
        this(null);
    }
    public MazeTableModel(Object[][] data) {
        if(data == null) this.data = new Object[1][columnNames.length];
        else this.data = data;
        mazes = new HashMap();
    }
    public void updateTable(Set<MGraph> mazes){
        //Update Mazes Set
        for(var m : mazes) {
            this.mazes.put(m.getLabel(), m);
        }

        //Convert mazes into data
        data = new Object[this.mazes.size()][getColumnCount()];
        int idx = 0;
        for(var m : mazes) {
            data[idx][0] = m.getLabel();
            data[idx][1] = m.getCreationDate();
            data[idx][2] = m.getSizeX();
            data[idx][3] = m.getSizeY();
            ++idx;
        }

        //Notifies Table to repaint
        fireTableChanged(new TableModelEvent(this));
    }

    public Set<MGraph> getMazes() {
        //Returns a copy
        return new HashSet<>(mazes.values());
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

    public Class<?> getColumnClass(int columnIndex) {
        return classes[columnIndex];
    }

    public MGraph getMaze(String key) {
        return mazes.get(key);
    }
}
