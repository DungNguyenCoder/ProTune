//package dungnguyen.protunefinal.controllers;
//
//import dungnguyen.protunefinal.models.PlaylistData;
//import dungnguyen.protunefinal.models.SongData;
//import dungnguyen.protunefinal.utilz.ShowAlert;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.control.Alert;
//import javafx.scene.control.ListView;
//
//import java.io.BufferedWriter;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.List;
//
//import static dungnguyen.protunefinal.utilz.Constants.SONG_DATA;
//import static dungnguyen.protunefinal.utilz.ShowDialog.showDialog;
//
//public class PlaylistController {
//    @FXML
//    private ListView<String> playlistView;
//
//    @FXML
//    private ListView<SongData> songListView;
//
//    private String currentUserName;
//
//    private MainController mainController;
//
//    public void setCurrentUserName(String username) {
//        this.currentUserName = currentUserName;
//        updatePlaylistView();
//    }
//
//    public void setMainController(MainController mainController) {
//        this.mainController = mainController;
//    }
//
//    @FXML
//    public void initialize() {
//
//        updatePlaylistView();
//
//        playlistView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue != null) {
//                showSongsInPlaylist(newValue);
//            }
//        });
//    }
//
//    private void updatePlaylistView() {
//        playlistView.getItems().clear();
//        for (String username : playlistManager.getAllPlaylists().keySet()) {
//            PlaylistData playlist = playlistManager.getPlaylist(username);
//            if (playlist != null) {
//                playlistView.getItems().add(playlist.getPlaylistName());
//            }
//        }
//    }
//
//    private void showSongsInPlaylist(String playlistName) {
//        songListView.getItems().clear();
//        for (PlaylistData playlist : playlistManager.getAllPlaylists().values()) {
//            if (playlist.getPlaylistName().equals(playlistName)) {
//                for (SongData song : playlist.getSongs()) {
//                    songListView.getItems().add(song);
//                }
//                break;
//            }
//        }
//    }
//
//    public void setPlaylists(List<PlaylistData> playlists) {
//        playlistView.getItems().clear();
//        for (PlaylistData playlist : playlists) {
//            playlistView.getItems().add(playlist.getPlaylistName());
//        }
//    }
//
//    @FXML
//    private void addPlaylist() {
//        String playlistName = showDialog("Add Playlist", "Enter playlist name:");
//        if (playlistName != null && !playlistName.isEmpty()) {
//            PlaylistData newPlaylist = new PlaylistData(playlistName, playlistName);
//            playlistManager.addPlaylist(playlistName, newPlaylist);
//            playlistManager.saveToFile(); // Lưu vào file JSON
//            updatePlaylistView();
//        }
//    }
//
//    @FXML
//    private void editPlaylist() {
//        String selectedPlaylist = playlistView.getSelectionModel().getSelectedItem();
//        if (selectedPlaylist != null) {
//            String newName = showDialog("Edit Playlist", "Enter new playlist name:");
//            if (newName != null && !newName.isEmpty()) {
//                PlaylistData playlist = playlistManager.getPlaylist(selectedPlaylist);
//                if (playlist != null) {
//                    playlist.setPlaylistName(newName);
//                    playlistManager.getAllPlaylists().remove(selectedPlaylist);
//                    playlistManager.addPlaylist(newName, playlist);
//                    playlistManager.saveToFile();
//                    updatePlaylistView();
//                }
//            }
//        } else {
//            new ShowAlert("Edit Error", "No playlist selected!", Alert.AlertType.ERROR);
//        }
//    }
//
//    @FXML
//    private void deletePlaylist() {
//        String selectedPlaylist = playlistView.getSelectionModel().getSelectedItem();
//        if (selectedPlaylist != null) {
//            playlistManager.getAllPlaylists().remove(selectedPlaylist);
//            playlistManager.saveToFile(); // Cập nhật file JSON
//            updatePlaylistView();
//        } else {
//            new ShowAlert("Delete Error", "No playlist selected!", Alert.AlertType.ERROR);
//        }
//    }
//}
