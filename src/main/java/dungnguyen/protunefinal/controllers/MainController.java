package dungnguyen.protunefinal.controllers;

import dungnguyen.protunefinal.MainApp;
import dungnguyen.protunefinal.models.SongData;
import dungnguyen.protunefinal.utilz.ShowAlert;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.io.*;
import java.util.ArrayList;

import static dungnguyen.protunefinal.utilz.Constants.*;
import static dungnguyen.protunefinal.utilz.LoadSave.deleteSongFromFile;
import static dungnguyen.protunefinal.utilz.LoadSave.updateSongInFile;

public class MainController {
    private MainApp mainApp;
    public static ArrayList<SongData> songData = new ArrayList<>();

    @FXML
    private BorderPane mainPane;

    @FXML
    private Label username;

    @FXML
    private HomeController homeController;

    @FXML
    private ControlController controlController;

    @FXML
    private LocalController localController;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setUsername(String username) {
        this.username.setText(username);
    }

    public String getUsername() {
        return username.getText();
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
            System.out.println(song);
            System.out.println();
        }
        new ShowAlert("message","Song added", Alert.AlertType.INFORMATION);
        handleHomeButton(null);
    }

    private void loadSongsFromFile() {
        File file = new File(SONG_DATA);
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\|");
                if (data.length == 6) {
                    SongData song = new SongData(data[0], data[1], data[2], data[3], data[4], data[5]);
                    songData.add(song);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() throws IOException {
        loadSongsFromFile();
        
        FXMLLoader homeLoader = new FXMLLoader(getClass().getResource(HOME));
        homeLoader.load();
        homeController = homeLoader.getController();

        FXMLLoader controlLoader = new FXMLLoader(getClass().getResource(CONTROL_VIEW));
        Parent musicControls = controlLoader.load();
        controlController = controlLoader.getController();

        if (controlController != null) {
            controlController.setHomeController(homeController);
            homeController.setControlController(controlController);
        } else {
            System.out.println("Control controller is null");
        }

        HBox hBox = new HBox(musicControls);
        hBox.setAlignment(Pos.CENTER);
        mainPane.setBottom(hBox);

    }

    @FXML
    void handleHomeButton(MouseEvent event) {
        homeController = loadPage(HOME);
        homeController.setControlController(controlController);
        homeController.setSongList(songData);
    }

    @FXML
    void handleAddSongButton(MouseEvent event) {
        AddController addSongController = loadPage(ADD);
        addSongController.setMainController(this);
    }

    @FXML
    void handleSearchButton(MouseEvent event) {
        SearchController searchSongController = loadPage(SEARCH);
        searchSongController.setMainController(this);
    }

    @FXML
    void handleMyLocalButton(MouseEvent event) {
        LocalController localController = loadPage(LOCAL);
        localController.setMainController(this);
        localController.setControlController(controlController);
        localController.setSongList(songData);
    }

    @FXML
    void handleLogoutButton(MouseEvent event) throws IOException {
        mainApp.showLoginScreen();
        songData.clear();
    }

    public void updateSongData(SongData oldSong, SongData updatedSong) {
        for (int i = 0; i < songData.size(); i++) {
            SongData song = songData.get(i);
            if (song.getSongName().equals(oldSong.getSongName()) && song.getArtist().equals(oldSong.getArtist())) {
                updateSongInFile(oldSong, updatedSong);
                songData.set(i, updatedSong);
                System.out.println("Updated song: " + updatedSong.getSongName());
                return;
            }
        }
        System.out.println("Song not found for update: " + updatedSong.getSongName());
    }

    public void removeSongData(SongData songToRemove) {
        boolean removed = songData.removeIf(song ->
                song.getSongName().equals(songToRemove.getSongName()) &&
                        song.getArtist().equals(songToRemove.getArtist())
        );
        if (removed) {
            deleteSongFromFile(songToRemove);
            System.out.println("Removed song: " + songToRemove.getSongName());
        } else {
            System.out.println("Song not found for removal: " + songToRemove.getSongName());
        }
    }


}
