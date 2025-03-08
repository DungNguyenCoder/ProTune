package dungnguyen.protunefinal;

import dungnguyen.protunefinal.controllers.MainController;
import dungnguyen.protunefinal.controllers.LoginController;
import dungnguyen.protunefinal.controllers.SignupController;
import dungnguyen.protunefinal.utilz.Constants;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

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
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource(Constants.LOGIN_SCREEN));
        Pane root = fxmlLoader.load();

        LoginController controller = fxmlLoader.getController();
        controller.setMainApp(this);

        stage.setScene(new Scene(root));
        stage.show();
    }

    public void showSignUpScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource(Constants.SIGNUP_SCREEN));
        Pane root = fxmlLoader.load();
        SignupController controller = fxmlLoader.getController();
        controller.setMainApp(this);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void showHomeScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource(Constants.HOME_SCREEN));
        Pane root = fxmlLoader.load();
        MainController controller = fxmlLoader.getController();
        controller.setMainApp(this);
        controller.setUsername(currentUsername);
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