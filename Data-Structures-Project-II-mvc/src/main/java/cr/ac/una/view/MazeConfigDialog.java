package cr.ac.una.view;

import javax.swing.*;
import java.awt.*;

public class MazeConfigDialog extends JDialog {
    private JLabel name;
    private JLabel size;
    private JTextField nameInput;
    private JTextField sizeXInput;
    private JTextField sizeYInput;
    private JButton accept;
    public MazeConfigDialog(Frame owner) {
        super(owner, true);
        setTitle("Configuración de Laberinto");
        setSize(400, 200);
        setLocationRelativeTo(owner);

        name = new JLabel("Nombre");
        size = new JLabel("Medidas");
        nameInput = new JTextField();
        sizeXInput = new JTextField();
        sizeYInput = new JTextField();
        accept = new JButton("Acceptar");


    }
}
