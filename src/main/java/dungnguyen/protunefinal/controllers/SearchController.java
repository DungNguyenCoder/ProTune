package dungnguyen.protunefinal.controllers;

import dungnguyen.protunefinal.models.SongData;
import dungnguyen.protunefinal.utilz.Constants;
import dungnguyen.protunefinal.utilz.ShowAlert;
import dungnguyen.protunefinal.utilz.SongListCell;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SearchController {

    private MainController mainController;

    @FXML
    private TextField songNameField;

    @FXML
    private ListView<SongData> searchSongListView;

    @FXML
    void handleSearchButton(ActionEvent event) {
        String searchText = songNameField.getText();
        if(!searchText.isEmpty()) {
            loadSongsFromFile();
        }
        else {
            new ShowAlert("Error","Missing field", Alert.AlertType.ERROR);
        }
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    private void loadSongsFromFile() {
        File file = new File(Constants.SONG_DATA);
        if (!file.exists()) return;
        searchSongListView.getItems().clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\|");
                if(data[0].toLowerCase().trim().contains(songNameField.getText().toLowerCase().trim())) {
                    searchSongListView.getItems().add(new SongData(data[0], data[1], data[2], data[3], data[4], data[5]));
                }
            }
            if(searchSongListView.getItems().isEmpty()) {
                System.out.println("No songs found");
                new ShowAlert("Message","No songs found", Alert.AlertType.INFORMATION);
            }
            else {
                searchSongListView.setCellFactory(listView -> new SongListCell());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
