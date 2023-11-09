package cr.ac.una.controller;

import cr.ac.una.model.MazeTableModel;
import cr.ac.una.model.ViewModel;
import cr.ac.una.util.graphs.MGraph;
import cr.ac.una.view.MainWindow;
import cr.ac.una.view.MazeConfigDialog;

import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainWindowController implements Controller {
    private final MainWindow window;
    private final MazeTableModel tableModel = new MazeTableModel();;
    private MazeConfigDialog mazeConfig;
    private final Set<MGraph> mazes;
    private final ExecutorService executor = new ThreadPoolExecutor(0, 1, 0L,
            TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(1));

    private final List<MazeViewController> mazeViewControllers;
    public MainWindowController() {
        window = new MainWindow(this);
        mazes = tableModel.getMazes();
        mazeViewControllers = new ArrayList<>();
        loadXMLData();
        window.init();
    }

    private void loadXMLData() {
        /*

                LOADS LAST XML FILE

         */
    }

    public TableModel getTableModel(){
        return tableModel;
    }

    public void bringWindowToFront(int selectedRow){
        System.out.println("Bringing window to front");
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
        MGraph newGraph = new MGraph(name, sizeX, sizeY);
        mazes.add(newGraph);
        tableModel.updateTable(mazes);
        ViewModel vm = new ViewModel(newGraph);
        mazeViewControllers.add(new MazeViewController(vm, executor));
    }

    public void saveProgramState() {
        System.out.println("Saving...");
    }

    public void openMazesFile() {
        System.out.println("Opening XML file");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        executor.execute(() -> {
            if(e.getSource().equals(window.getTable())) {
                if(e.getClickCount() == 2) bringWindowToFront(window.getTable().getSelectedRow());
            } else if(e.getSource().equals(window.getButton())) {
                createNewMaze();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        executor.execute(() -> {
            if(e.getSource().equals(window.getNewItem())) {
                createNewMaze();
            } else if(e.getSource().equals(window.getOpenItem())) {
                openMazesFile();
            } else if(e.getSource().equals(window.getSaveItem())) {
                saveProgramState();
            }
        });
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
