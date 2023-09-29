package cr.ac.una.service;


import javax.sound.sampled.*;
import java.io.IOException;
import java.util.Objects;

public class Sounds implements LineListener {
    public static Sounds instance() {
        if (theInstance == null) {
            theInstance = new Sounds();
        }
        return theInstance;
    }
    private Sounds(){}

    public void playSound(Tracks track){
        try(AudioInputStream audioStream =
                    AudioSystem.getAudioInputStream(
                            Objects.requireNonNull(getClass().getResourceAsStream(track.getName())));
            Clip audioClip = AudioSystem.getClip()){
                audioClip.addLineListener(this);
                audioClip.open(audioStream);
                if(track.equals(Tracks.TIMER)) {
                    audioClip.setLoopPoints(0, (int) ((audioClip.getMicrosecondLength() - 1_000_000) / 1_000_000));
                    audioClip.setLoopPoints();
                }
                audioClip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }
    private static Sounds theInstance;

    @Override
    public void update(LineEvent event) {

    }

    public enum Tracks {
        CORRECT("correct.wav"),
        TIMER("timer.wav"),
        YOUR_TURN("yourTurnSound.wav"),
        BLIP("sequenceSound.wav"),
        FAIL("Fail.wav"),
        SUCCESS("successSound.wav"),
        START_BEEP("gameStart.wav");
        Tracks(String name){
            this.name = name;
        }
        private String name;
        public String getName(){
            return name;
        }
    }
}