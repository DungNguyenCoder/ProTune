package dungnguyen.protunefinal.controllers;

import dungnguyen.protunefinal.MainApp;
import dungnguyen.protunefinal.utilz.Constants;
import dungnguyen.protunefinal.utilz.ShowAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.*;

public class SignupController {

    private MainApp mainApp;

    @FXML
    private TextField firstnameField;

    @FXML
    private TextField lastnameField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleSignUpButtonClick(ActionEvent event) throws IOException {
        String firstname = firstnameField.getText();
        String lastname = lastnameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if(firstname.isEmpty() || lastname.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            System.out.println("Missing fields");
            new ShowAlert("Error","Missing fields",Alert.AlertType.ERROR);
            return;
        }

        if(!password.equals(confirmPassword)) {
            System.out.println("Passwords do not match");
            new ShowAlert("Error","Passwords do not match", Alert.AlertType.ERROR);
            return;
        }

        if(registerUser(firstname, lastname, username, password)) {
            System.out.println("User registered successfully");
            new ShowAlert("Success","User successfully registered",Alert.AlertType.INFORMATION);
            mainApp.showLoginScreen();
        }
        else {
            new ShowAlert("Error","Username might already exist",Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleBackToSignInButtonClick(ActionEvent event) throws IOException {
        mainApp.showLoginScreen();
    }

    public boolean registerUser(String firstName, String lastName, String username, String password) {
        File file = new File(Constants.USERS_LOGIN_DATA);
        if(isUsernameExists(username, file)) {
            firstnameField.clear();
            lastnameField.clear();
            usernameField.clear();
            passwordField.clear();
            confirmPasswordField.clear();
            return false;
        }

        try(PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
            writer.println(firstName + " " + lastName + " " + username + " " + password);
            return true;
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isUsernameExists(String username, File file) {
        if (!file.exists()) return false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(" ");
                if (userData.length >= 4) {
                    String storedUsername = userData[2];
                    if (storedUsername.equals(username)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
