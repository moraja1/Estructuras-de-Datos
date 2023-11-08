package cr.ac.una.controller;

import org.junit.Test;

import static org.junit.Assert.*;

public class MainWindowControllerTest {
    MainWindowController mwc = new MainWindowController();
    @Test
    public void isDigit() {
        System.out.println(mwc.isDigit(""));
        System.out.println(mwc.isDigit("0"));
        System.out.println(mwc.isDigit("564654685"));
        System.out.println(mwc.isDigit("dfhgdfgdfd"));
    }
}