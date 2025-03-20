package dungnguyen.protunefinal.utilz;

import javafx.scene.control.Alert;

public class ShowAlert {
    public ShowAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
