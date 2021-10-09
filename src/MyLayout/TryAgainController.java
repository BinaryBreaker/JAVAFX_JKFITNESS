package MyLayout;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class TryAgainController {

    @FXML
    JFXButton TryAgain;



    @FXML
    public void Reload(){
        Stage primaryStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SplashScreen.fxml"));
        Parent parent = null;
        try {
            parent = (Parent) fxmlLoader.load();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        primaryStage.setScene(new Scene(parent));
        //set stage borderless
        primaryStage.setTitle("JK-Fitness");
        primaryStage.getIcons().add(new Image("C:\\Users\\MMH\\Desktop\\JAVAFX\\src\\images\\logo.png"));
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        Stage stage = (Stage) TryAgain.getScene().getWindow();

        stage.close();
        primaryStage.show();
    }
}
