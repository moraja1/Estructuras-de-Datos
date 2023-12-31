package cr.ac.una.view;

import cr.ac.una.controller.Controller;
import cr.ac.una.controller.MainWindowController;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.awt.event.WindowListener;

public class MainWindow extends JFrame implements TableModelListener {
    private final MainWindowController controller;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem saveItem;
    private JMenuItem openItem;
    private JMenuItem newItem;
    private JTable table;
    private JButton button;
    public MainWindow(Controller controller) {
        super("Laberintos");
        this.controller = (MainWindowController) controller;

        // Crear la barra de menú
        menuBar = new JMenuBar();
        fileMenu = new JMenu("Archivo");
        saveItem = new JMenuItem("Guardar", null);
        openItem = new JMenuItem("Abrir", null);
        newItem = new JMenuItem("Nuevo", null);
        fileMenu.add(saveItem);
        fileMenu.add(openItem);
        fileMenu.add(newItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        //Creo un borderLayout
        BorderLayout bl = new BorderLayout();
        setLayout(bl);

        // Crear la tabla
        table = new JTable(((MainWindowController) controller).getTableModel());
        table.getModel().addTableModelListener(this);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(getWidth() - 10, getHeight() - 10));
        table.setFillsViewportHeight(true);

        // Crear el botón
        button = new JButton("Nuevo");

        // Creo contenedores para los componentes
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        buttonPanel.add(button, BorderLayout.WEST);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tablePanel.add(scrollPane, BorderLayout.CENTER);


        // Añado todos los componentes
        add(tablePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Configurar la ventana
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void init(){
        table.addMouseListener(controller);
        button.addMouseListener(controller);
        saveItem.addActionListener(controller);
        openItem.addActionListener(controller);
        newItem.addActionListener(controller);
        setVisible(true);
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        table.repaint();
    }

    public JMenuItem getSaveItem() {
        return saveItem;
    }

    public JMenuItem getOpenItem() {
        return openItem;
    }

    public JMenuItem getNewItem() {
        return newItem;
    }

    public JTable getTable() {
        return table;
    }

    public JButton getButton() {
        return button;
    }
}
