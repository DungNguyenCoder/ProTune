package dungnguyen.protunefinal;

import dungnguyen.protunefinal.controllers.LoginController;
import dungnguyen.protunefinal.controllers.MainController;
import dungnguyen.protunefinal.controllers.SignupController;
import dungnguyen.protunefinal.utilz.Constants;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

import static dungnguyen.protunefinal.utilz.Constants.*;

public class MainApp extends Application {

    private static Stage stage;
    private String currentUsername;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        stage.setTitle("ProTune");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("images/logos/app.png")));
        showLoginScreen();
    }

    public void showLoginScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource(LOGIN_SCREEN));
        Pane root = fxmlLoader.load();

        LoginController loginController = fxmlLoader.getController();
        loginController.setMainApp(this);

        stage.setScene(new Scene(root));
        stage.show();
    }

    public void showSignUpScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource(SIGNUP_SCREEN));
        Pane root = fxmlLoader.load();
        SignupController signupController = fxmlLoader.getController();
        signupController.setMainApp(this);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void showMainScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource(HOME_SCREEN));
        Pane root = fxmlLoader.load();
        MainController mainController = fxmlLoader.getController();
        mainController.setMainApp(this);
        mainController.setUsername(currentUsername);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void setCurrentUsername(String currentUsername) {
        this.currentUsername = currentUsername;
    }

    public String getCurrentUsername() {
        return currentUsername;
    }

    public static void main(String[] args) {
        launch();
    }
}