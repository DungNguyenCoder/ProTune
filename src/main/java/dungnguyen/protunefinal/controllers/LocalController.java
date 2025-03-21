package dungnguyen.protunefinal.controllers;

import dungnguyen.protunefinal.models.SongData;
import dungnguyen.protunefinal.utilz.SongListCell;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.GridPane;

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

                myLocalListView.setOnContextMenuRequested(this::showContextMenu);
            });
        }
    }

    private void showContextMenu(ContextMenuEvent contextMenuEvent) {
        int selectedIndex = myLocalListView.getSelectionModel().getSelectedIndex();
        if(selectedIndex < 0) {
            System.out.println("no local selected");
            return;
        }

        ContextMenu contextMenu = new ContextMenu();
        MenuItem editItem = new MenuItem("Edit");
        MenuItem deleteItem = new MenuItem("Delete");

        editItem.setOnAction(event -> editSong(selectedIndex));
        deleteItem.setOnAction(event -> deleteSong(selectedIndex));

        contextMenu.getItems().addAll(editItem, deleteItem);
        contextMenu.show(myLocalListView, contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY());
    }

    private void editSong(int selectedIndex) {
        SongData song = myLocalListView.getItems().get(selectedIndex);
        SongData oldSong = new SongData(song.getSongName(), song.getArtist(), song.getPlaylist(), song.getThumbnailPath(), song.getFilePath(), song.getUploader());
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit song");
        dialog.setHeaderText("Edit song information");

        GridPane grid = new GridPane();
        TextField titleField = new TextField(song.getSongName());
        TextField artistField = new TextField(song.getArtist());
        TextField playlistField = new TextField(song.getPlaylist());

        grid.addRow(0, new Label("New song name:"), titleField);
        grid.addRow(1, new Label("New artist:"), artistField);
        grid.addRow(2, new Label("New playlist:"), playlistField);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                song.setSongName(titleField.getText());
                song.setArtist(artistField.getText());
                song.setPlaylist(playlistField.getText());

//
//                System.out.println("---");
//                System.out.println(oldSong);
//                System.out.println("---");
//                System.out.println(song);
                myLocalListView.refresh();
                mainController.updateSongData(oldSong, song);
            }
        });
    }

    private void deleteSong(int selectedIndex) {
        SongData song = myLocalListView.getItems().remove(selectedIndex);
        if (song != null) {
            mainController.removeSongData(song);
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
