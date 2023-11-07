package cr.ac.una.controller;

import cr.ac.una.model.MazeTableModel;
import cr.ac.una.util.graphs.MGraph;
import cr.ac.una.view.MainWindow;
import cr.ac.una.view.MazeConfigDialog;

import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainWindowController implements Controller, MouseListener, ActionListener {
    private final MainWindow window;
    private final MazeTableModel tableModel = new MazeTableModel();;
    private final Set<MGraph> mazes;
    private final ExecutorService executor = new ThreadPoolExecutor(0, 1, 0L,
            TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(1));
    public MainWindowController() {
        window = new MainWindow(this);
        mazes = tableModel.getMazes();
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

    public void createNewMaze() {
        executor.execute(() -> {
            new MazeConfigDialog(window);
        });
        mazes.add(new MGraph());
        tableModel.updateTable(mazes);
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
