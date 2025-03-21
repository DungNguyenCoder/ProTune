package dungnguyen.protunefinal.utilz;

import dungnguyen.protunefinal.models.SongData;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.File;

public class SongListCell extends ListCell<SongData> {
    private final ImageView imageView = new ImageView();
    private final Label songNameLabel = new Label();
    private final Label artistLabel = new Label();
    private final BorderPane borderPane = new BorderPane();

    public SongListCell() {
        BorderPane.setMargin(imageView, new Insets(5, 20, 5, 10));
    }

    @Override
    protected void updateItem(SongData song, boolean empty) {
        super.updateItem(song, empty);
        if (empty || song == null) {
            setText(null);
            setGraphic(null);
        } else {
            File imageFile = new File("image/" + song.getThumbnailPath());
            if (imageFile.exists()) {
                Image image = new Image(imageFile.toURI().toString());
                imageView.setImage(image);
                imageView.setFitHeight(100);
                imageView.setPreserveRatio(true);
            } else {
                imageView.setImage(null);
            }

            songNameLabel.setText(song.getSongName());
            artistLabel.setText(song.getArtist());

            VBox textBox = new VBox(10,songNameLabel, artistLabel);
            borderPane.setLeft(textBox);
            borderPane.setRight(imageView);

            setGraphic(borderPane);
        }
    }
}
