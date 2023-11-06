package cr.ac.una.controller;

import cr.ac.una.model.MazeTableModel;
import cr.ac.una.view.MainWindow;

import javax.swing.table.AbstractTableModel;

public class MainWindowController implements Controller{
    private final MainWindow window;
    public MainWindowController() {
        window = new MainWindow(this);
        window.init();
    }

    public AbstractTableModel getTableModel(){
        return new MazeTableModel();
    }

    public void bringWindowToFront(int selectedRow){

    }

    public void createNewMaze() {
        System.out.println("Maze creado");
    }

    public void saveProgramState() {
    }

    public void openMazesFile() {
    }
}
