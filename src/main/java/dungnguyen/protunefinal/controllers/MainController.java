package dungnguyen.protunefinal.controllers;

import dungnguyen.protunefinal.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class MainController {
    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private Label username;

    @FXML
    private BorderPane mainPane;

    public void setUsername(String username) {
        this.username.setText(username);
    }

    @FXML
    private void initialize() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/dungnguyen/protunefinal/views/fxml/control-view.fxml"));
        Parent musicControls = fxmlLoader.load();
        HBox hBox = new HBox(musicControls);
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setPadding(new Insets(0,200,0,0));
        mainPane.setBottom(hBox);
        loadPage("/dungnguyen/protunefinal/views/fxml/home.fxml");
    }

    @FXML
    private void handleHomeButton(ActionEvent event) {
        loadPage("/dungnguyen/protunefinal/views/fxml/home.fxml");
    }

    @FXML
    private void handleSearchButton(ActionEvent event) {
        loadPage("/dungnguyen/protunefinal/views/fxml/search.fxml");
    }

    @FXML
    private void handleAddSongButton(ActionEvent event) {
        loadPage("/dungnguyen/protunefinal/views/fxml/add.fxml");
    }

    @FXML
    private void handleMyLocalButton(ActionEvent event) {
        loadPage("/dungnguyen/protunefinal/views/fxml/local.fxml");
    }

    @FXML
    private void handlePlaylistButton(ActionEvent event) {
        loadPage("/dungnguyen/protunefinal/views/fxml/playlist.fxml");
    }

    @FXML
    private void handleLogoutButton(ActionEvent event) throws IOException {
        mainApp.showLoginScreen();
    }

    private void loadPage(String fxml) {
        try {
            Parent page = FXMLLoader.load(getClass().getResource(fxml));
            mainPane.setCenter(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
