package cr.ac.una.view;

import cr.ac.una.controller.MainWindowController;
import org.junit.Test;

import static org.junit.Assert.*;

public class MainWindowTest {
    public static void main(String[] args) {
        new MainWindow(new MainWindowController());
    }

}