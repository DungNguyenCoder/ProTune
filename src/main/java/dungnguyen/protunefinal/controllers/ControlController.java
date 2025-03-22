package dungnguyen.protunefinal.controllers;

import dungnguyen.protunefinal.models.SongData;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
    private Slider progressSlider = new Slider();
    @FXML
    private Slider volumeSlider = new Slider();
    @FXML
    private ImageView playButton;
    @FXML
    private ImageView repeatButton;
    @FXML
    private ImageView randomButton;
    @FXML
    private Label volumeLabel;
    @FXML
    private Label timeLabel;

    private HomeController homeController;
    private LocalController localController;
    private MediaPlayer mediaPlayer;
    private List<SongData> songList = MainController.songData;
    private int currentIndex = 0;
    private boolean isSeeking = false;

    private boolean isRepeat = false;
    private boolean isRandom = false;


    public ControlController() {

    }

    private void setupListeners() {
        if (mediaPlayer == null) return;

        mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            if (!isSeeking) {
                progressSlider.setValue(newTime.toSeconds());
                updateTimeLabel(newTime, mediaPlayer.getTotalDuration());
            }
        });

        mediaPlayer.setOnReady(() -> {
            progressSlider.setMax(mediaPlayer.getTotalDuration().toSeconds());
            updateTimeLabel(mediaPlayer.getCurrentTime(), mediaPlayer.getTotalDuration());
        });

        mediaPlayer.setOnPlaying(() -> {
            progressSlider.setMax(mediaPlayer.getTotalDuration().toSeconds());
            updateTimeLabel(mediaPlayer.getCurrentTime(), mediaPlayer.getTotalDuration());
        });

        progressSlider.setOnMousePressed(event -> isSeeking = true);
        progressSlider.setOnMouseReleased(event -> {
            if (mediaPlayer != null) {
                mediaPlayer.seek(Duration.seconds(progressSlider.getValue()));
            }
            isSeeking = false;
        });

        volumeSlider.setValue(50);
        volumeLabel.setText("50%");
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (mediaPlayer != null) {
                mediaPlayer.setVolume(newVal.doubleValue() / 100);
            }
            volumeLabel.setText(String.format("%.0f%%", newVal.doubleValue()));
        });
    }

    private void updateTimeLabel(Duration currentTime, Duration totalDuration) {
        if (timeLabel == null) return;

        String currentTimeStr = formatTime(currentTime);
        String totalTimeStr = formatTime(totalDuration);
        timeLabel.setText(currentTimeStr + " / " + totalTimeStr);
    }

    private String formatTime(Duration duration) {
        if (duration == null || duration.toSeconds() <= 0) return "00:00";
        int minutes = (int) duration.toMinutes();
        int seconds = (int) duration.toSeconds() % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public void playSongAtIndex(int index) {
        System.out.println("playSongAtIndex: " + index);
        System.out.println("songList: " + songList.size());
        if (index < 0 || index >= songList.size()) {
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

        if(homeController != null) {
            homeController.highlightCurrentSong(index);
        }

        if(localController != null) {
            localController.highlightCurrentSong(index);
        }

        setupListeners();

        mediaPlayer.setOnEndOfMedia(() -> {
            if (isRepeat) {
                mediaPlayer.seek(Duration.ZERO);
                mediaPlayer.play();
            }
            else {
                handleNextClick(null);
            }
        });
    }


    @FXML
    public void handlePlayClick(MouseEvent mouseEvent) {
        System.out.println("handlePlayClick: " + mouseEvent);

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
        System.out.println("handleRandomClick: " + mouseEvent);
        isRandom = !isRandom;
        randomButton.setImage(new Image(getClass().getResourceAsStream(isRandom ? SUFFLE_ON : SUFFLE_OFF)));
    }

    @FXML
    public void handleRepeatClick(MouseEvent mouseEvent) {
        System.out.println("handleRepeatClick: " + mouseEvent);
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
        System.out.println("handlePreviousClick: " + mouseEvent);
        if (songList == null || songList.isEmpty()) return;

        currentIndex = (currentIndex - 1 + songList.size()) % songList.size();

        playSongAtIndex(currentIndex);
    }

    @FXML
    public void handleNextClick(MouseEvent mouseEvent) {
        System.out.println("handleNextClick: " + mouseEvent);
        if (songList == null || songList.isEmpty()) return;

        if (isRandom) {
            Random rand = new Random();
            int newIndex;
            do {
                newIndex = rand.nextInt(songList.size());
            } while (newIndex == currentIndex && songList.size() > 1);
            currentIndex = newIndex;
        } else {
            currentIndex = (currentIndex + 1) % songList.size();
        }

        playSongAtIndex(currentIndex);
    }

    public void setHomeController(HomeController homeController) {
        if(homeController == null) {
            System.out.println("homeController is null");
            return;
        }
        this.homeController = homeController;
        System.out.println("homeController set");
    }

    public void setLocalController(LocalController localController) {
        if(localController == null) {
            System.out.println("localController is null");
            return;
        }
        this.localController = localController;
        System.out.println("localController set");
    }
}
