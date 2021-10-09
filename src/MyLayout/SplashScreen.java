package MyLayout;

import MyLayout.Model.Credentials;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SplashScreen implements Initializable {

    @FXML
    HBox hBox;
    public static JavaSocket javaSocket;
    @FXML
    ImageView imageView;
    private String URL = Controller.URL_SITE;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new SplashLoader().start();
    }

    class SplashLoader extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                javaSocket = new JavaSocket();
                javaSocket.startConnection("0.0.0.0", 9999);

                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        Credentials credentials = new Credentials();
                        if (credentials.ReadData()) {
                            PostData(credentials);
                        } else {
                            Stage primaryStage = new Stage();
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
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
                            Stage stage = (Stage) hBox.getScene().getWindow();
                            stage.close();
                            primaryStage.show();
                        }

                    }
                });


            } catch (
                    Exception e) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Stage stage = (Stage) hBox.getScene().getWindow();
                        stage.close();
                    }
                });
            }
        }

    }

    private void PostData(Credentials credentials) {
        String charset = "UTF-8";
        String requestURL = URL + "/api/login";
        MultipartUtility multipart = null;
        try {
            multipart = new MultipartUtility(requestURL, charset);
            multipart.addFormField("username", credentials.getUsername());
            multipart.addFormField("password", credentials.getPassword());
            JsonObject response = new Gson().fromJson(multipart.finish(), JsonObject.class);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    int Status = response.get("status").getAsInt();
                    if (Status == 200) {
                        MultipartUtility.Username = credentials.getUsername();
                        MultipartUtility.Password = credentials.getPassword();
                        Stage primaryStage = new Stage();
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DashBoard.fxml"));
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
                        Stage stage = (Stage) hBox.getScene().getWindow();
                        stage.close();
                        primaryStage.show();

                    } else {
                        Stage primaryStage = new Stage();
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
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
                        Stage stage = (Stage) hBox.getScene().getWindow();
                        stage.close();
                        primaryStage.show();
                    }
                }
            });
        } catch (IOException e) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Stage primaryStage = new Stage();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NetworkError.fxml"));
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
                    Stage stage = (Stage) hBox.getScene().getWindow();
                    stage.close();
                    primaryStage.show();
                }
            });
        }
    }


}
