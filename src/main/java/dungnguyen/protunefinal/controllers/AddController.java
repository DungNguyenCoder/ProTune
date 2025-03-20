package dungnguyen.protunefinal.controllers;

import dungnguyen.protunefinal.models.SongData;
import dungnguyen.protunefinal.utilz.ShowAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.*;

import static dungnguyen.protunefinal.utilz.Constants.*;

public class AddController {

    private MainController mainController;

    @FXML
    private TextField songNameField;

    @FXML
    private TextField artistField;

    @FXML
    private TextField playlistField;

    @FXML
    private TextField thumbnailField;

    @FXML
    private TextField linkMP3Field;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public AddController() {
        // Constructor mặc định (bắt buộc để JavaFX có thể khởi tạo)
    }

    private void saveSongToFile(SongData song) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SONG_DATA, true))) {
            String formattedSong = String.format("%s|%s|%s|%s|%s|%s%n",
                    song.getSongName(), song.getArtist(), song.getPlaylist(), song.getThumbnailPath(), song.getFilePath(), song.getUploader());
            writer.write(formattedSong);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleChooseFileButton(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MP3 Files", "*.mp3"));

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                File musicDir = new File("music");
                if (!musicDir.exists()) {
                    musicDir.mkdirs();
                }

                // Copy file vào thư mục dự án
                File destinationFile = new File(musicDir, selectedFile.getName());
                try (InputStream in = new FileInputStream(selectedFile);
                     OutputStream out = new FileOutputStream(destinationFile)) {

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = in.read(buffer)) > 0) {
                        out.write(buffer, 0, length);
                    }
                }
                linkMP3Field.setText(selectedFile.getName());
            } catch (IOException e) {
                e.printStackTrace();
                new ShowAlert("Error", "Can't open MP3 file", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void handleThumbnailButton(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png"));

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                File musicDir = new File("image");
                if (!musicDir.exists()) {
                    musicDir.mkdirs();
                }
                File destinationFile = new File(musicDir, selectedFile.getName());
                try (InputStream in = new FileInputStream(selectedFile);
                     OutputStream out = new FileOutputStream(destinationFile)) {

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = in.read(buffer)) > 0) {
                        out.write(buffer, 0, length);
                    }
                }
                thumbnailField.setText(selectedFile.getName());

            } catch (IOException e) {
                e.printStackTrace();
                new ShowAlert("Error", "Can't open image file", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void handleAddSongButton(ActionEvent event) {
        String songName = songNameField.getText();
        String artist = artistField.getText();
        String playlist = playlistField.getText();
        String thumbnail = thumbnailField.getText();
        String link = linkMP3Field.getText();
        String uploader = mainController.getUsername();

        if(songName.isEmpty() || artist.isEmpty() || playlist.isEmpty() || thumbnail.isEmpty() || link.isEmpty()) {
            new ShowAlert("Error", "Missing fields", Alert.AlertType.ERROR);
            return;
        }

        SongData newSong = new SongData(songName, artist, playlist, thumbnail, link, uploader);
        mainController.addSong(newSong);

        saveSongToFile(newSong);

        songNameField.clear();
        artistField.clear();
        playlistField.clear();
        thumbnailField.clear();
        linkMP3Field.clear();
    }
}
