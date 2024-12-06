import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SimpleAudioPlayer {
    private Clip clip;
    private AudioInputStream audioInputStream;
    private String filePath;
    private boolean loop;

    // Constructor to load the audio file
    public SimpleAudioPlayer(String filePath, boolean loop) {
        this.filePath = filePath;
        this.loop = loop;
        loadAudio();
    }

    // Load the audio file
    private void loadAudio() {
        try {
            File audioFile = new File(filePath);
            if (!audioFile.exists()) {
                throw new IOException("Audio file not found: " + filePath);
            }

            audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        } catch (UnsupportedAudioFileException e) {
            System.err.println("Unsupported audio file format: " + e.getMessage());
        } catch (LineUnavailableException e) {
            System.err.println("Audio line unavailable: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error loading audio file: " + e.getMessage());
        }
    }

    // Play the audio
    public void play() {
        if (clip != null && !clip.isRunning()) {
            clip.start();
        }
    }

    // Pause the audio
    public void pause() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    // Resume the audio
    public void resume() {
        if (clip != null && !clip.isRunning()) {
            clip.start();
        }
    }

    // Restart the audio
    public void restart() {
        if (clip != null) {
            clip.stop();
            clip.setFramePosition(0); // Reset to the beginning
            clip.start();
        }
    }

    // Stop the audio completely
    public void stop() {
        if (clip != null) {
            clip.stop();
            clip.close();
        }
    }

    // Close resources when done
    public void close() {
        if (clip != null) {
            clip.close();
        }
        try {
            if (audioInputStream != null) {
                audioInputStream.close();
            }
        } catch (IOException e) {
            System.err.println("Error closing audio input stream: " + e.getMessage());
        }
    }

    // Check if the audio is playing
    public boolean isPlaying() {
        return clip != null && clip.isRunning();
    }
}