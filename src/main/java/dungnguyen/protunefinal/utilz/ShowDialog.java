package dungnguyen.protunefinal.utilz;

import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class ShowDialog {
    public static String showDialog(String title, String message) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText(null);
        dialog.setContentText(message);
        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }
}
