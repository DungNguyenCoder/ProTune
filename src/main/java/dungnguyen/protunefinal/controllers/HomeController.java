package dungnguyen.protunefinal.controllers;

import dungnguyen.protunefinal.models.SongData;
import dungnguyen.protunefinal.utilz.SongListCell;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.ArrayList;

public class HomeController {

    @FXML
    private ListView<SongData> songListView;


    private ControlController controlController = new ControlController();
    private int index;

    public HomeController() {

    }


    public void setSongList(ArrayList<SongData> songData) {
        if (songListView != null) {

            songListView.getItems().clear();
            songListView.getItems().addAll(songData);

            songListView.setCellFactory(listView -> new SongListCell());

            songListView.setOnMouseClicked((event) -> {
                if (event.getClickCount() == 2) {
                    index = songListView.getSelectionModel().getSelectedIndex();
                    if(controlController != null && index >= 0) {
                        System.out.println("home: " + index);
                        controlController.playSongAtIndex(index);
                        controlController.setHomeController(this);
                    }
                    else {
                        System.out.println("controller is null");
                    }
                }
            });
        }
    }

    public void setControlController(ControlController controlController) {
        if(controlController == null) {
            System.out.println("controlController is null");
            return;
        }
        this.controlController = controlController;
        System.out.println("controller set");
    }

    public void highlightCurrentSong(int index) {
        if (index < 0 || index >= songListView.getItems().size()) return;

        songListView.getSelectionModel().clearSelection();
        songListView.getSelectionModel().select(index);
        songListView.scrollTo(index);
    }
}
