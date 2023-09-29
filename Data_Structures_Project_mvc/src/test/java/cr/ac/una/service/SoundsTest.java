package cr.ac.una.service;

import static org.junit.jupiter.api.Assertions.*;

class SoundsTest {
    public static void main(String[] args) {
        Sounds.instance().playSound(Sounds.Tracks.SUCCESS);
        try{
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Sounds.instance().playSound(Sounds.Tracks.TIMER);
        try{
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Sounds.instance().playSound(Sounds.Tracks.CORRECT);
    }
}