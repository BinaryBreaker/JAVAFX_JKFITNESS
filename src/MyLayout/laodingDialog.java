package MyLayout;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class laodingDialog implements Initializable {
    @FXML
    ImageView imageView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File file = new File("C:\\Users\\MMH\\Desktop\\JAVAFX\\src\\images\\890-loading-animation.gif");
        Image im = new Image(file.toURI().toString());
        imageView.setImage(im);
    }
}
