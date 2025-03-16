package dungnguyen.protunefinal.controllers;

import dungnguyen.protunefinal.models.SongData;
import dungnguyen.protunefinal.utilz.Constants;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.util.List;
import java.util.Random;

import static dungnguyen.protunefinal.utilz.Constants.*;

public class ControlController {

    @FXML
    private Slider progressSlider;
    @FXML
    private Slider volumeSlider;
    @FXML
    private ImageView playButton;
    @FXML
    private ImageView repeatButton;
    @FXML
    private ImageView randomButton;

    private HomeController homeController;
    private MediaPlayer mediaPlayer;
    private List<SongData> songList;
    private int currentIndex = 0;

    private boolean isRepeat = false;
    private boolean isRandom = false;

    public ControlController() {

    }

    public void setSongList(List<SongData> songList) {
        this.songList = songList;
    }

    private void setupListeners() {
        if (mediaPlayer == null) return;

        mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            progressSlider.setValue(newTime.toSeconds());
        });

        progressSlider.setOnMouseReleased(event -> {
            if (mediaPlayer != null) {
                mediaPlayer.seek(Duration.seconds(progressSlider.getValue()));
            }
        });

        mediaPlayer.setOnReady(() -> {
            progressSlider.setMax(mediaPlayer.getTotalDuration().toSeconds());
        });

        mediaPlayer.setOnPlaying(() -> {
            progressSlider.setMax(mediaPlayer.getTotalDuration().toSeconds());
        });

        volumeSlider.setValue(50);
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (mediaPlayer != null) {
                mediaPlayer.setVolume(newVal.doubleValue() / 100);
            }
        });

        mediaPlayer.setOnReady(() -> {
            progressSlider.setMax(mediaPlayer.getTotalDuration().toSeconds());
        });
    }

    public void playSongAtIndex(int index) {
        if (songList == null || songList.isEmpty() || index < 0 || index >= songList.size()) {
            System.out.println("index is out of bounds");
            return;
        }

        currentIndex = index;
        SongData selectedSong = songList.get(index);
        if (selectedSong == null) {
            return;
        }

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }

        File file = new File("music/" + selectedSong.getFilePath());
        if (!file.exists()) {
            System.out.println("File not found");
            return;
        }

        Media media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();

        setupListeners();
    }


    @FXML
    public void handlePlayClick(MouseEvent mouseEvent) {
        if (mediaPlayer == null) {
            System.out.println("HomeController not initialized");
            return;
        }

        if(mediaPlayer == null) {
            System.out.println("No media player available");
            return;
        }

        if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.pause();
            playButton.setImage(new Image(getClass().getResourceAsStream(PLAY)));
        } else {
            mediaPlayer.play();
            playButton.setImage(new Image(getClass().getResourceAsStream(PAUSE)));
        }

    }

    @FXML
    public void handleRandomClick(MouseEvent mouseEvent) {
        isRandom = !isRandom;
        randomButton.setImage(new Image(getClass().getResourceAsStream(isRandom ? SUFFLE_ON : SUFFLE_OFF)));
    }

    @FXML
    public void handleRepeatClick(MouseEvent mouseEvent) {
        isRepeat = !isRepeat;
        repeatButton.setImage(new Image(getClass().getResourceAsStream(isRepeat ? REPEAT_ON : REPEAT_OFF)));

        if (mediaPlayer != null) {
            mediaPlayer.setOnEndOfMedia(() -> {
                if (isRepeat) {
                    mediaPlayer.seek(Duration.ZERO);
                    mediaPlayer.play();
                } else {
                    handleNextClick(null);
                }
            });
        }
    }

    @FXML
    public void handlePreviousClick(MouseEvent mouseEvent) {
        if (songList == null || songList.isEmpty()) return;

        currentIndex = (currentIndex - 1 + songList.size()) % songList.size();

        playSongAtIndex(currentIndex);
    }

    @FXML
    public void handleNextClick(MouseEvent mouseEvent) {
        if (songList == null || songList.isEmpty()) return;

        if (isRandom) {
            Random rand = new Random();
            currentIndex = rand.nextInt(songList.size());
        } else {
            currentIndex = (currentIndex + 1) % songList.size();
        }
        playSongAtIndex(currentIndex);
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
        if(this.mediaPlayer == null) {
            System.out.println("mediaPlayer is null");
            return;
        }
        setupListeners();
        System.out.println("mediaPlayer initialized");
    }
}
