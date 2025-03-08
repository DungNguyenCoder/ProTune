package dungnguyen.protunefinal.controllers;

import dungnguyen.protunefinal.MainApp;
import dungnguyen.protunefinal.utilz.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;

public class LoginController {
    private MainApp mainApp;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleLoginButtonClick(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if(authenticate(username, password)) {
            try {
                mainApp.setCurrentUsername(getName(username));
                mainApp.showHomeScreen();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Login Successful");
        }
        else {
            System.out.println("Invalid username or password");
            usernameField.clear();
            passwordField.clear();
            showAlert("Error", "Invalid username or password!", Alert.AlertType.ERROR);
        }
    }

    private boolean authenticate(String username, String password) {
        File file = new File(Constants.USERS_LOGIN);
        if(!file.exists()) {
            System.out.println("File not found");
            return false;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(Constants.USERS_LOGIN))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(" ");
                if (userData.length >= 4) {
                    String storedUsername = userData[2];
                    String storedPassword = userData[3];
                    if (storedUsername.equals(username) && storedPassword.equals(password)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading usersdata.txt");
        }
        return false;
    }

    private String getName(String username) {
        File file = new File(Constants.USERS_LOGIN);
        if(!file.exists()) {
            System.out.println("File not found");
            return "";
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(" ");
                if (userData.length >= 4) {
                    String storedFirstName = userData[0];
                    String storedLastName = userData[1];
                    String storedUsername = userData[2];
                    if(storedUsername.equals(username)) {
                        return storedFirstName + " " + storedLastName;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading usersdata.txt");
        }
        return "";
    }

    @FXML
    private void handleSignUpButtonClick(ActionEvent event) throws IOException {
        mainApp.showSignUpScreen();
    }

    @FXML
    private void handleLinkFacebookButtonClick(ActionEvent event) throws IOException {
        openLink(Constants.FACEBOOK_LINK);
    }

    @FXML
    private void handleLinkGithubButtonClick(ActionEvent event) throws IOException {
        openLink(Constants.GITHUB_LINK);
    }


    private void openLink(String link) {
        try {
            Desktop.getDesktop().browse(new URI(link));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
