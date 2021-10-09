package MyLayout;

import MyLayout.Model.Credentials;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {
    @FXML
    JFXTextField username;
    @FXML
    JFXTextField password;
    @FXML
    JFXDialog dialog;
    @FXML
    StackPane stackpane;

    Credentials credentials;

    String URL = Controller.URL_SITE;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        credentials = new Credentials();
    }


    @FXML
    public void Submit() {
        if (CheckPassword() & CheckUsername()) {
            ShowWaitingDialog("Checking Credential's", "Verifying Credentials please wait");
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    PostData(credentials);
                }
            });

            thread.start();
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
                        credentials.SaveData();
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
                        Stage stage = (Stage) password.getScene().getWindow();

                        stage.close();
                        primaryStage.show();
                    } else {
                        System.out.println( response.get("msg").getAsString());
                        ShowErrorDialog("Invalid Credentials", response.get("msg").getAsString());
                    }
                }
            });
        } catch (IOException e) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    ShowErrorDialog("Connection Error", "Error Connecting to server");
                }
            });
        }
    }


    private boolean CheckPassword() {
        JFXTextField var = password;
        String value = var.getText().toString().trim();
        if (value.length() < 1 || value.contains(" ")) {
            ShowErrorDialog("Invalid Credentials", "Please Provide a Valid password");
            return false;
        }

        credentials.setPassword(value);
        return true;
    }

    private boolean CheckUsername() {
        JFXTextField var = username;
        String value = var.getText().toString().trim();
        if (value.length() < 1 || value.contains(" ")) {
            ShowErrorDialog("Invalid Credentials", "Please Provide a Valid Username");
            return false;
        }

        credentials.setUsername(value);
        return true;
    }

    private void ShowErrorDialog(String heading, String Text) {
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label(heading));
        layout.setBody(new Label(Text));
        dialog.setDialogContainer(stackpane);
        dialog.setContent(layout);
        dialog.setTransitionType(JFXDialog.DialogTransition.RIGHT);
        dialog.setOverlayClose(true);
        dialog.show();
    }

    private void ShowWaitingDialog(String heading, String Text) {
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label(heading));
        layout.setBody(new Label(Text));
        dialog.setDialogContainer(stackpane);
        dialog.setContent(layout);
        dialog.setTransitionType(JFXDialog.DialogTransition.RIGHT);
        dialog.setOverlayClose(false);
        dialog.show();
    }
}
