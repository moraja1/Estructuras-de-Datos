package cr.ac.una.view;

import cr.ac.una.controller.Controller;
import cr.ac.una.controller.MainWindowController;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private final MainWindowController controller;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem saveItem;
    private JMenuItem openItem;
    private JMenuItem newItem;
    private JTable table;
    private JButton boton;
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

        // Crear la tabla
        String[] columnas = {"Nombre", "Apellido", "Edad"};
        Object[][] datos = {{"Juan", "Pérez", 25}, {"María", "García", 30}, {"Pedro", "López", 35}};
        table = new JTable(datos, columnas);
        table.setSize(getWidth() - 25, getHeight() - 25);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        scrollPane.setSize(getWidth() - 25, getHeight() - 25);

        // Crear el botón
        boton = new JButton("Botón");
        add(boton, BorderLayout.SOUTH);

        // Configurar la ventana
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
