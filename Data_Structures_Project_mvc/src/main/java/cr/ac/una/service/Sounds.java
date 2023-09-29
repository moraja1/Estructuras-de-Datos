package cr.ac.una.service;


import javax.sound.sampled.*;
import java.io.IOException;
import java.util.Objects;

public class Sounds implements LineListener, Runnable{
    private static Sounds theInstance;
    private boolean isPlaybackCompleted = false;
    private Clip clip;
    private Thread mixer;
    private Tracks track;

    public static Sounds instance() {
        if (theInstance == null) {
            theInstance = new Sounds();
        }
        return theInstance;
    }

    private Sounds() {
    }

    public void playSound(Tracks track) {
        this.track = track;
        if(mixer != null) {
            while (mixer.isAlive()) isPlaybackCompleted = true;
            mixer = null;
        }
        mixer = new Thread(this, "Sound Player");
        mixer.start();
    }

    private void stopSound() {
        clip.flush();
        clip.stop();
        clip.close();
    }

    @Override
    public void update(LineEvent event) {
        if(event.getType().equals(LineEvent.Type.STOP)) {
            isPlaybackCompleted = true;
        }
    }

    @Override
    public void run() {
        isPlaybackCompleted = false;
        try {
            clip = AudioSystem.getClip();
            clip.addLineListener(this);
            AudioInputStream ais = AudioSystem.getAudioInputStream(
                    Objects.requireNonNull(Sounds.class.getResourceAsStream(track.getName()))
            );
            clip.open(ais);
            clip.start();
            while(!isPlaybackCompleted) {
                clip.isActive();
            }
            stopSound();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isPlaybackCompleted() {
        return isPlaybackCompleted;
    }

    public enum Tracks {
        CORRECT("correct.wav"),
        TIMER("timer.wav"),
        YOUR_TURN("yourTurnSound.wav"),
        BLIP("sequenceSound.wav"),
        FAIL("Fail.wav"),
        SUCCESS("successSound.wav"),
        START_BEEP("gameStart.wav");

        Tracks(String name) {
            this.name = name;
        }

        private String name;

        public String getName() {
            return name;
        }
    }
}