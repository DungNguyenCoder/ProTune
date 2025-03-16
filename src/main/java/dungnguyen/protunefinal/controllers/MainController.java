package dungnguyen.protunefinal.controllers;

import dungnguyen.protunefinal.MainApp;
import dungnguyen.protunefinal.models.PlaylistData;
import dungnguyen.protunefinal.models.SongData;
import dungnguyen.protunefinal.utilz.Constants;
import dungnguyen.protunefinal.utilz.ShowAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.io.*;
import java.util.ArrayList;

import static dungnguyen.protunefinal.utilz.Constants.*;

public class MainController {
    private MainApp mainApp;
    private ArrayList<SongData> songData = new ArrayList<>();
    private ArrayList<PlaylistData> playlists = new ArrayList<>();

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
        File file = new File(Constants.SONG_DATA);
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

    private void loadPlaylists() {
        File file = new File(PLAYLIST_DATA);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length > 1) {
                    String username = parts[0];
                    PlaylistData playlist = new PlaylistData(username);

                    for (int i = 1; i < parts.length; i++) {
                        String[] songData = parts[i].split(",");
                        if (songData.length == 6) {
                            SongData song = new SongData(songData[0], songData[1], songData[2], songData[3], songData[4], songData[5]);
                            playlist.addSong(song);
                        }
                    }
                    playlists.add(playlist);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void savePlaylists() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PLAYLIST_DATA))) {
            for (PlaylistData playlist : playlists) {
                StringBuilder sb = new StringBuilder();
                sb.append(playlist.getUsername());
                for (SongData song : playlist.getSongs()) {
                    sb.append("|").append(song.getSongName()).append(",").append(song.getArtist())
                            .append(",").append(song.getPlaylist()).append(",").append(song.getThumbnailPath())
                            .append(",").append(song.getFilePath());
                }
                writer.write(sb.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addSongToPlaylist(String username, SongData song) {
        for (PlaylistData playlist : playlists) {
            if (playlist.getUsername().equals(username)) {
                playlist.addSong(song);
                savePlaylists();
                return;
            }
        }

        PlaylistData newPlaylist = new PlaylistData(username);
        newPlaylist.addSong(song);
        playlists.add(newPlaylist);
        savePlaylists();
    }

    @FXML
    private void initialize() throws IOException {
        FXMLLoader homeLoader = new FXMLLoader(getClass().getResource(HOME));
        Parent homeParent = homeLoader.load();
        homeController = homeLoader.getController();

        FXMLLoader controlLoader = new FXMLLoader(getClass().getResource(CONTROL_VIEW));
        Parent musicControls = controlLoader.load();
        controlController = controlLoader.getController();

        if (controlController != null) {
            homeController.setControlController(controlController);
            controlController.setHomeController(homeController);
        } else {
            System.out.println("Control controller is null");
        }

        HBox hBox = new HBox(musicControls);
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setPadding(new Insets(0,200,0,0));
        mainPane.setBottom(hBox);

        loadSongsFromFile();
    }

    @FXML
    void handleHomeButton(ActionEvent event) {
        HomeController homeController = loadPage(HOME);
        homeController.setSongList(songData);
        controlController.setSongList(songData);
    }

    @FXML
    void handleAddSongButton(ActionEvent event) {
        AddController addSongController = loadPage(ADD);
        addSongController.setMainController(this);
    }

    @FXML
    void handleSearchButton(ActionEvent event) {
        SearchController searchSongController = loadPage(SEARCH);
        searchSongController.setMainController(this);
    }

    @FXML
    void handleMyLocalButton(ActionEvent event) {
        LocalController localController = loadPage(LOCAL);
        localController.setMainController(this);
        localController.setSongList(songData);
        controlController.setSongList(songData);
    }

    @FXML
    void handlePlaylistButton(ActionEvent event) {
        PlaylistController playlistController = loadPage(PLAYLIST);
        playlistController.setPlaylists(playlists);
    }

    @FXML
    void handleLogoutButton(ActionEvent event) throws IOException {
        mainApp.showLoginScreen();
    }
}
