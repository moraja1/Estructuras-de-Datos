package cr.ac.una.controller;

import cr.ac.una.model.MazeTableModel;
import cr.ac.una.model.ViewModel;
import cr.ac.una.util.graphs.MGraph;
import cr.ac.una.util.service.xml.XMLDom;
import cr.ac.una.view.MainWindow;
import cr.ac.una.view.MazeConfigDialog;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

public class MainWindowController implements Controller {
    private final MainWindow window;
    private final MazeTableModel tableModel = new MazeTableModel();;
    private MazeConfigDialog mazeConfig;
    private Set<MGraph> mazes;
    private static final ExecutorService executor = new ThreadPoolExecutor(0, 2, 0L,
            TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(1));
    private final List<MazeViewController> mazeViewControllers;

    public MainWindowController() {
        window = new MainWindow(this);
        mazes = tableModel.getMazes();
        mazeViewControllers = new ArrayList<>();
        window.init();
    }

    private void loadXMLData() {
        XMLDom xml = new XMLDom();
        Set<MGraph> list = xml.loadData();
        Iterator<MGraph> iterator = list.iterator();
        while (iterator.hasNext()){
            MGraph maze = iterator.next();
            mazes.add(maze);
            tableModel.updateTable(mazes);
            SwingUtilities.invokeLater(() -> {
                ViewModel vm = new ViewModel(maze);
                mazeViewControllers.add(new MazeViewController(vm, executor));
            });
        }
    }

    private void saveXMLData(){
        XMLDom xml = new XMLDom();
        xml.saveData(mazes);
    }

    public TableModel getTableModel(){
        return tableModel;
    }

    public void bringWindowToFront(int selectedRow){
        String mazeName = (String) tableModel.getValueAt(selectedRow, 0);
        var mGraph = tableModel.getMaze(mazeName);
        for(var mazeVC : mazeViewControllers) {
            if (mazeVC.ownsMaze(mGraph)) {
                if(mazeVC.getWindow().isDisplayable()) mazeVC.getWindow().toFront();
                else mazeVC.createWindow();
            }
        }
    }

    public void createNewMaze(){
        mazeConfig = new MazeConfigDialog(window, this);
        mazeConfig.init();
        String name = "";
        String sizeX = "";
        String sizeY = "";
        if(mazeConfig.isAccepted()) {
            name = mazeConfig.getNameInput();
            sizeX = mazeConfig.getSizeXInput();
            sizeY = mazeConfig.getSizeYInput();
            Integer size_x = (!sizeX.isBlank() && isDigit(sizeX)) ? Integer.parseInt(sizeX) : null;
            Integer size_y = (!sizeY.isBlank() && isDigit(sizeY)) ? Integer.parseInt(sizeY) : null;
            createNewMaze(name.isBlank() ? null : name, size_x, size_y);
        }
        mazeConfig = null;
    }

    public boolean isDigit(String value) {
        return value.matches("^\\d+$");
    }

    public void createNewMaze(String name, Integer sizeX, Integer sizeY) {
        MGraph newGraph = new MGraph(name, sizeX, sizeY, true);
        mazes.add(newGraph);
        tableModel.updateTable(mazes);
        SwingUtilities.invokeLater(() -> {
            ViewModel vm = new ViewModel(newGraph);
            mazeViewControllers.add(new MazeViewController(vm, executor));
        });
    }

    public void saveProgramState() {
        System.out.println("Saving...");
    }

    public void openMazesFile() {
        System.out.println("Opening XML file");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        try {
            executor.execute(() -> {
                if(e.getSource().equals(window.getTable())) {
                    if(e.getClickCount() == 2) bringWindowToFront(window.getTable().getSelectedRow());
                } else if(e.getSource().equals(window.getButton())) {
                    createNewMaze();
                }
            });
        } catch (RejectedExecutionException ignored) {}
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            executor.execute(() -> {
                if(e.getSource().equals(window.getNewItem())) {
                    createNewMaze();
                } else if(e.getSource().equals(window.getOpenItem())) {
                    openMazesFile();
                    loadXMLData();
                } else if(e.getSource().equals(window.getSaveItem())) {
                    saveProgramState();
                    saveXMLData();
                }
            });
        } catch (RejectedExecutionException ignored) {}
    }

    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}
