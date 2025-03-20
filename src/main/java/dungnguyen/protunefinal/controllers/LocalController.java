package dungnguyen.protunefinal.controllers;

import dungnguyen.protunefinal.models.SongData;
import dungnguyen.protunefinal.utilz.SongListCell;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class LocalController {
    @FXML
    private ListView<SongData> myLocalListView;

    private ControlController controlController;
    private MainController mainController;
    private int index;

    public LocalController() {

    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setSongList(ArrayList<SongData> songData) {
        if (myLocalListView != null) {
            myLocalListView.getItems().clear();
            for(SongData song : songData) {
                System.out.println(song.getUploader() + " " + mainController.getUsername());
                if(song.getUploader().equals(mainController.getUsername())) {
                    myLocalListView.getItems().add(song);
                }
            }

            myLocalListView.setCellFactory(listView -> new SongListCell());

            myLocalListView.setOnMouseClicked((event) -> {
                if (event.getClickCount() == 2) {
                    index = myLocalListView.getSelectionModel().getSelectedIndex();
                    if(controlController != null && index >= 0) {
                        System.out.println("local: " + index);
                        controlController.playSongAtIndex(index);
                        controlController.setLocalController(this);
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
        if (index < 0 || index >= myLocalListView.getItems().size()) return;

        myLocalListView.getSelectionModel().clearSelection();
        myLocalListView.getSelectionModel().select(index);
        myLocalListView.scrollTo(index);
    }
}
