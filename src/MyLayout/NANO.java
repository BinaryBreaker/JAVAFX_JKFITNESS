package MyLayout;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class NANO extends Application {
    private double x, y;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("SplashScreen.fxml"));
        primaryStage.setScene(new Scene(root));
        //set stage borderless
        primaryStage.setTitle("JK-Fitness");
        primaryStage.getIcons().add(new Image("C:\\Users\\MMH\\Desktop\\JAVAFX\\src\\images\\logo.png"));
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();

        //drag it here
    }


    public static void main(String[] args) {
        launch(args);
    }
}
