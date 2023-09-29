package cr.ac.una.service;


import javax.sound.sampled.*;
import java.io.IOException;
import java.util.Objects;

public class Sounds implements LineListener {
    private static Sounds theInstance;
    Clip audioClip;
    private boolean isPlaybackCompleted = false;
    private Thread player;

    public static Sounds instance() {
        if (theInstance == null) {
            theInstance = new Sounds();
        }
        return theInstance;
    }

    private Sounds() {
    }

    public void playSound(Tracks track) {
        player = new Thread(() -> {
            try (AudioInputStream audioStream =
                         AudioSystem.getAudioInputStream(
                                 Objects.requireNonNull(getClass().getResourceAsStream(track.getName())))) {
                audioClip = AudioSystem.getClip();
                audioClip.addLineListener(this);
                audioClip.open(audioStream);
                if (track.equals(Tracks.TIMER)) {
                    audioClip.setLoopPoints(0, 315200);
                    audioClip.loop(Clip.LOOP_CONTINUOUSLY);
                }
                audioClip.start();
                while (!isPlaybackCompleted) {
                }
                stopSound();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                throw new RuntimeException(e);
            }
        });
        player.start();
    }

    private void stopSound() {
        isPlaybackCompleted = false;
        audioClip.stop();
        audioClip.close();
    }

    public void setPlaybackCompleted(boolean playbackCompleted) {
        isPlaybackCompleted = playbackCompleted;
    }

    @Override
    public void update(LineEvent event) {
        if (event.getType().equals(LineEvent.Type.START)) {
            isPlaybackCompleted = false;
        } else if (event.getType().equals(LineEvent.Type.STOP)) {
            isPlaybackCompleted = true;
        }
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