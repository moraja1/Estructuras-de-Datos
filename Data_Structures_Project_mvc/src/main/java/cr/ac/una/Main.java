package cr.ac.una;

import cr.ac.una.logic.ViewController;
import cr.ac.una.service.Configuration;
import cr.ac.una.service.Sounds;

public class Main {

    public static void main(String[] args) {
        new Main().init();
    }

    private void init() {
        Sounds.instance().playSound(Sounds.Tracks.SUCCESS);
        System.out.println();
//        ViewController controller = new ViewController(Configuration.getInstance());
//        controller.init();
    }

}