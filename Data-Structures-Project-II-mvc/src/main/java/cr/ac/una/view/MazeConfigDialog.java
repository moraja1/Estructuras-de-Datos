package cr.ac.una.view;

import cr.ac.una.controller.Controller;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;

public class MazeConfigDialog extends JDialog implements KeyListener {
    private JTextField nameInput;
    private JTextField sizeXInput;
    private JTextField sizeYInput;
    private JButton accept;
    private boolean accepted = false;
    public MazeConfigDialog(Frame owner, Controller controller) {
        super(owner, true);
        setTitle("Configuración de Laberinto");
        setSize(400, 200);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());
        setResizable(false);
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(400, 200));


        nameInput = new JTextField();
        sizeXInput = new JTextField();
        sizeYInput = new JTextField();
        sizeXInput.addKeyListener(this);
        sizeYInput.addKeyListener(this);
        sizeXInput.setMinimumSize(new Dimension(150, 20));
        sizeYInput.setMinimumSize(new Dimension(150, 20));
        sizeXInput.setPreferredSize(new Dimension(150, 20));
        sizeYInput.setPreferredSize(new Dimension(150, 20));
        sizeXInput.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
        sizeYInput.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
        accept = new JButton("Acceptar");
        accept.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                accepted = true;
                closeDialog();
            }
        });
        accept.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    public void init(){
        //Creo las etiquetas
        final JLabel name = new JLabel("Nombre");
        final JLabel size = new JLabel("Medidas");

        //Creo el contenedor de la parte norte de la ventana
        final JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.X_AXIS));
        northPanel.add(Box.createRigidArea(new Dimension(0,10)));
        northPanel.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
        name.setLabelFor(nameInput);
        northPanel.add(name);
        northPanel.add(Box.createRigidArea(new Dimension(10,0)));
        northPanel.add(nameInput);

        //Creo el contenedor del centro de la ventana
        final JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        centerPanel.add(Box.createRigidArea(new Dimension(0,10)));

        //Contenedor de etiqueta medidas
        final JPanel centerSubOne = new JPanel();
        centerSubOne.setLayout(new BoxLayout(centerSubOne, BoxLayout.X_AXIS));
        size.setLabelFor(centerSubOne);
        size.setAlignmentX(Component.LEFT_ALIGNMENT);
        size.setAlignmentY(Component.TOP_ALIGNMENT);
        centerSubOne.add(size);
        centerSubOne.add(Box.createHorizontalGlue());

        //Contenedor de JTextFields de entradas
        final JPanel centerSubTwo = new JPanel();
        centerSubTwo.setLayout(new BoxLayout(centerSubTwo, BoxLayout.X_AXIS));


        //Contenedor de entrada de X
        final JPanel centerSubX = new JPanel();
        centerSubX.setLayout(new BoxLayout(centerSubX, BoxLayout.Y_AXIS));
        final JLabel sizeX = new JLabel("X");
        sizeX.setLabelFor(sizeXInput);
        sizeX.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerSubX.add(sizeX);
        centerSubX.add(Box.createHorizontalGlue());
        centerSubX.add(sizeXInput);
        centerSubTwo.add(centerSubX);
        centerSubTwo.add(Box.createRigidArea(new Dimension(25,0)));

        //Contenedor de entrada de Y
        JPanel centerSubY = new JPanel();
        centerSubY.setLayout(new BoxLayout(centerSubY, BoxLayout.Y_AXIS));
        final JLabel sizeY = new JLabel("Y");
        sizeY.setLabelFor(sizeYInput);
        sizeY.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerSubY.add(Box.createHorizontalGlue());
        centerSubY.add(sizeY);
        centerSubY.add(sizeYInput);
        centerSubTwo.add(centerSubY);

        //Contenedor del Boton
        final JPanel southPanel = new JPanel();
        southPanel.setLayout(new BorderLayout());
        southPanel.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
        southPanel.add(accept, BorderLayout.WEST);

        //Agrego a la ventana
        centerPanel.add(centerSubOne);
        centerPanel.add(centerSubTwo);
        add(northPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                nameInput.setText("");
                sizeXInput.setText("");
                sizeYInput.setText("");
                accepted = false;
                closeDialog();
            }
        });
        setVisible(true);
    }

    public String getNameInput() {
        return nameInput.getText();
    }

    public String getSizeXInput() {
        return sizeXInput.getText();
    }

    public String getSizeYInput() {
        return sizeYInput.getText();
    }

    public JButton getAccept() {
        return accept;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void closeDialog() {
        dispose();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (!Character.isDigit(c)) {
            e.consume();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
