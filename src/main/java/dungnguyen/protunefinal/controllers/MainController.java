package dungnguyen.protunefinal.controllers;

import dungnguyen.protunefinal.MainApp;
import dungnguyen.protunefinal.models.SongData;
import dungnguyen.protunefinal.utilz.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MainController {
    private MainApp mainApp;
    private ArrayList<SongData> songData = new ArrayList<>();

    @FXML
    private BorderPane mainPane;

    @FXML
    private Label username;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setUsername(String username) {
        this.username.setText(username);
    }

    private <T> T loadPage(String page) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(page));
            Parent parent = fxmlLoader.load();

            mainPane.setCenter(parent);

            return fxmlLoader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addSong(SongData newSong) {
        songData.add(newSong);
        System.out.println("Song added");
        for(SongData song : songData) {
            song.printSongInfo(song);
        }
        handleHomeButton(null);
    }

    private void loadSongsFromFile() {
        File file = new File(Constants.SONG_DATA);
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\|");
                if (data.length == 5) {
                    SongData song = new SongData(data[0], data[1], data[2], data[3], data[4]);
                    songData.add(song);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Constants.CONTROL_VIEW));
        Parent musicControls = fxmlLoader.load();
        HBox hBox = new HBox(musicControls);
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setPadding(new Insets(0,200,0,0));
        mainPane.setBottom(hBox);
        loadSongsFromFile();
        loadPage(Constants.HOME);
    }

    @FXML
    void handleHomeButton(ActionEvent event) {
        HomeController homeController = loadPage(Constants.HOME);
        homeController.setSongList(songData);
    }

    @FXML
    void handleAddSongButton(ActionEvent event) {
        AddController addSongController = loadPage(Constants.ADD);
        addSongController.setMainController(this);
    }

    @FXML
    void handleSearchButton(ActionEvent event) {

    }

    @FXML
    void handleMyLocalButton(ActionEvent event) {

    }

    @FXML
    void handlePlaylistButton(ActionEvent event) {

    }

    @FXML
    void handleLogoutButton(ActionEvent event) throws IOException {
        mainApp.showLoginScreen();
    }
}
