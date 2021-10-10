package MyLayout;

import MyLayout.Model.*;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import com.zkteco.biometric.FingerprintSensorErrorCode;
import com.zkteco.biometric.FingerprintSensorEx;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import static com.googlecode.javacv.cpp.opencv_highgui.cvSaveImage;

public class Controller implements Initializable, TemplateSetter {
    public static final String URL_SITE = "http://127.0.0.1:8000";
    public final static String PUT = "PUT";
    public final static String POST = "POST";
    public final static String Registration = "Registration";
    public final static String Update = "Search | Update";
    private String CurrentMethod = POST;

    String Validate_Dialog = "";

    String path = null;

    File file = null;
    @FXML
    VBox Update_Package;
    PackageData dataPackteNew;
    @FXML
    JFXTextField PackageDate;

    @FXML
    StackPane stackpane;
    @FXML
    JFXDialog dialog;
    @FXML
    ScrollPane scrollPane;

    @FXML

    JFXTextField Name;
    @FXML

    JFXTextField FatherName;
    @FXML
    JFXTextField IdCard;
    @FXML
    JFXTextField DOB;
    @FXML
    JFXTextField PhoneNo;
    @FXML
    JFXTextField Email;
    @FXML
    JFXTextField EmName;
    @FXML
    JFXTextField EmContact;
    @FXML
    JFXTextField Profession;
    @FXML
    JFXTextArea Address;
    @FXML
    ImageView Fingerprint;
    @FXML
    ImageView IDcardImageback;
    @FXML
    ImageView IDcardImageFront;
    @FXML
    JFXCheckBox BloodPressure;
    @FXML
    JFXCheckBox HeartDisease;
    @FXML
    JFXCheckBox Diabetes;
    @FXML
    JFXCheckBox Hepatitus;
    @FXML
    JFXCheckBox Asthama;
    @FXML
    JFXCheckBox Arthritis;

    @FXML
    JFXRadioButton Male;
    @FXML
    JFXRadioButton Female;
    @FXML
    ImageView DP;
    @FXML
    Label label;

    @FXML
    TextField search;
    @FXML
    TextField PackageSearch;

    @FXML
    JFXButton Submit;

    @FXML
    StackPane stackPaneSearchUpdate;

    Client client;

    @FXML
    VBox vBox;
    @FXML
    VBox MainVBox;
    @FXML
    HBox DpHBox;

    @FXML
    JFXComboBox Packagedetail;

    Stage stage;

    Stage LoadingDialog;

    @FXML
    Text PersonName;
    @FXML
    Text PersonPhoneNo;
    @FXML
    Text PersonCNIC;
    @FXML
    Text PersonStartDate;
    @FXML
    Text PersonEndDate;
    @FXML
    Text PersonCurrent;
    @FXML
    ImageView PersonDp;
    ToggleGroup toggle;


    @FXML
    Text FormName;
    @FXML
    Text FormFatherName;
    @FXML
    Text FormCNIC;
    @FXML
    Text FormProfession;

    @FXML
    Text FormDOB;
    @FXML
    Text FormEMAIL;
    @FXML
    Text FormStatus;
    @FXML
    Text FormCell;
    @FXML
    Text FormDiseases;
    @FXML
    Text FormAddress;
    @FXML
    Text FormEmContact;
    @FXML
    Text FormEmName;
    @FXML
    VBox FORMVBOX;
    @FXML
    ImageView FormImage;

    Credentials credentials;
    JavaSocket javaSocket;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        javaSocket = SplashScreen.javaSocket;

        credentials = new Credentials();
        credentials.ReadData();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(javaSocket.sendMessage("{\"Status\":101,\"Username\":\"" + credentials.getUsername() + "\",\"Password\":\"" + credentials.getPassword() + "\"}"));
                } catch (IOException e) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    Stage stage = (Stage) FORMVBOX.getScene().getWindow();
                                    stage.close();
                                }
                            });
                        }
                    });
                }
            }
        }).start();


        stackpane.getChildren().remove(Update_Package);
        JFXDialogLayout layout = new JFXDialogLayout();
        dialog.setDialogContainer(stackpane);
        dialog.setContent(layout);
        dialog.setTransitionType(JFXDialog.DialogTransition.RIGHT);
        if (stackpane.getChildren().contains(FORMVBOX)) {
            stackpane.getChildren().remove(FORMVBOX);
        }


        IDcardImageback.setImage(new Image(new File("C:\\Users\\MMH\\Desktop\\JAVAFX\\src\\images\\BACk SIDE.jpg").toURI().toString()));
        IDcardImageFront.setImage(new Image(new File("C:\\Users\\MMH\\Desktop\\JAVAFX\\src\\images\\FrontId.jpg").toURI().toString()));
        Fingerprint.setImage(new Image(new File("C:\\Users\\MMH\\Desktop\\JAVAFX\\src\\images\\fingerprint.bmp").toURI().toString()));


        client = new Client();
        stackPaneSearchUpdate.setVisible(false);
        stackPaneSearchUpdate.managedProperty().bind(stackPaneSearchUpdate.visibleProperty());
        Circle clip = new Circle(500);
        DP.setClip(clip);
        ShowLoadingDilaog();

        toggle = new ToggleGroup();
        Male.setToggleGroup(toggle);
        Female.setToggleGroup(toggle);
        Color a = Color.rgb(64, 89, 169);
        Male.setSelectedColor(a);
        Female.setSelectedColor(a);

        Validator(Name);
        Validator(FatherName);
        Validator(IdCard);
        Validator(DOB);
        Validator(PhoneNo);
        Validator(Email);
        Validator(EmName);
        Validator(EmContact);
        Validator(Profession);
        Validator(Address);

        search.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    if ((search.getText().toString().trim()).length() > 0) {
                        GetData((search.getText().toString().trim()).replace(" ", "%20"));

                    }
                }
            }


        });
        PackageSearch.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    if ((PackageSearch.getText().toString().trim()).length() > 0) {
                        GetPackageData(((PackageSearch.getText().toString()).trim()).replace(" ", "%20"));

                    }
                }
            }


        });

    }

    @FXML
    public void SearchPackageUser() {
        dialog.close();
        if ((PackageSearch.getText().toString().trim()).length() > 0) {
            GetPackageData(((PackageSearch.getText().toString()).trim()).replace(" ", "%20"));

        }

    }

    @FXML
    public void SearchUser() {
        dialog.close();
        if ((search.getText().toString().trim()).length() > 0) {
            GetData((search.getText().toString().trim()).replace(" ", "%20"));

        }
    }

    private void GetPackageData(String datas) {
        RestDataPackete();
        LoadingDialog.show();
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                HttpURLConnection connection = null;
                String line;
                StringBuffer responsecontent = new StringBuffer();
                BufferedReader reader;
                try {
                    URL url = new URL(URL_SITE + "/api/Package/" + datas);
                    connection = (HttpURLConnection) url.openConnection();
                    String encoded = Base64.getEncoder().encodeToString((credentials.getUsername() + ":" + credentials.getPassword()).getBytes(StandardCharsets.UTF_8));  //Java 8
                    connection.setRequestProperty("Authorization", "Basic " + encoded);
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);

                    int status = connection.getResponseCode();
                    System.out.println(status);

                    if (status < 200) {
                        reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                        while ((line = reader.readLine()) != null) {
                            responsecontent.append(line);
                        }
                        reader.close();
                    } else {
                        reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        while ((line = reader.readLine()) != null) {
                            responsecontent.append(line);
                        }
                        reader.close();
                    }
                    JsonObject response = new Gson().fromJson(responsecontent.toString(), JsonObject.class);
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            int Status = response.get("status").getAsInt();
                            if (Status == 404) {
                                ShowDialog("Not Found", "User Not Found");

                            } else {
                                PackageData[] packa = new Gson().fromJson(response.get("msg"), PackageData[].class);
                                if (packa.length == 0) {
                                    ShowDialog("Not Found", "User Not Found");
                                    LoadingDialog.close();
                                    return;
                                }
                                LoadingDialog.close();
                                ShowDataPackete(packa);
                            }
                        }
                    });

                    connection.disconnect();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            ShowDialog("Connection Error", "PC isn't connected to the Internet");
                            LoadingDialog.close();
                        }
                    });


                }
            }
        });
        thread.start();
    }


    private void RestDataPackete() {
        dataPackteNew = null;
        PersonName.setText("");
        PersonPhoneNo.setText("");
        PersonCNIC.setText("");
        PersonStartDate.setText("");
        PersonEndDate.setText("");
        PersonCurrent.setText("");
        PackageDate.setText("");
        PersonDp.setImage(null);
        Packagedetail.setItems(null);
    }

    private void ShowDataPackete(PackageData[] packa) {
        ArrayList<User> users = new ArrayList<User>();
        for (PackageData pac : packa) {
            users.add(new User(pac.getPerson_Name(), pac.getCNIC(), pac.getEmail(), pac.getPhone_No()));
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TabelView.fxml"));
        Parent parent = null;
        try {
            parent = (Parent) fxmlLoader.load();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        TableViewDilaog tableViewDilaog = fxmlLoader.<TableViewDilaog>getController();
        tableViewDilaog.setTableContent(users);
        tableViewDilaog.setClickedRow(new ClickedRow() {
            @Override
            public void ClickedRowCNIC(String text) {
                for (PackageData cl : packa) {
                    if (cl.getCNIC().equals(text)) {
                        if (cl.getStatus().toLowerCase().equals("blocked")) {
                            ShowDialog("Access Denied", "Client has been blocked by the owner");
                            return;
                        }
                        dataPackteNew = cl;
                        Packagedetail.setItems(FXCollections.observableList(dataPackteNew.getAvailable_Packages()));
                        PersonName.setText(dataPackteNew.getPerson_Name());
                        PersonPhoneNo.setText(dataPackteNew.getPhone_No());
                        PersonCNIC.setText(dataPackteNew.getCNIC());
                        PersonStartDate.setText(dataPackteNew.getStartDate());
                        PersonEndDate.setText(dataPackteNew.getEndDate());
                        PersonCurrent.setText(dataPackteNew.getCurrent_Package());
                        PersonDp.setImage(new Image(URL_SITE + dataPackteNew.getDp_Pic()));
                        break;
                    }
                }

            }
        });
        Scene scene = new Scene(parent);
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();


    }

    private void Change_package() {
        if (stackpane.getChildren().contains(Update_Package)) {
            stackpane.getChildren().remove(Update_Package);
            stackpane.getChildren().add(vBox);
        }

    }

    @FXML
    protected void Update() {
        dialog.close();

        Change_package();
        CurrentMethod = PUT;
        stackPaneSearchUpdate.setVisible(true);
        label.setText(Update);
        stackPaneSearchUpdate.managedProperty().bind(stackPaneSearchUpdate.visibleProperty());
        DP.setVisible(false);
        DpHBox.setVisible(false);
        MainVBox.setVisible(false);
        EmptyAllFields();
        ShowLoadingDilaog();
        scrollPane.setVvalue(0);
        Submit.setText("UPDATE");
        if (stackpane.getChildren().contains(FORMVBOX)) {
            stackpane.getChildren().remove(FORMVBOX);
        }

    }


    @FXML
    protected void RegisterClient() {
        dialog.close();
        Change_package();
        CurrentMethod = POST;
        stackPaneSearchUpdate.setVisible(false);
        stackPaneSearchUpdate.managedProperty().bind(stackPaneSearchUpdate.visibleProperty());
        DP.setVisible(true);
        DpHBox.setVisible(true);
        MainVBox.setVisible(true);
        label.setText(Registration);
        EmptyAllFields();
        Submit.setText("Register");


    }

    @FXML
    protected void Package() {
        dialog.close();
        if (stackpane.getChildren().contains(vBox)) {

            EmptyAllFields();
            stackpane.getChildren().remove(vBox);
            stackpane.getChildren().add(Update_Package);

        }
        if (stackpane.getChildren().contains(FORMVBOX)) {
            stackpane.getChildren().remove(FORMVBOX);
        }

        RestDataPackete();
    }


    private void ShowLoadingDilaog() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/LoadingDilaog.fxml"));
        Parent parent = null;
        try {
            parent = (Parent) fxmlLoader.load();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        laodingDialog cameraDialog = fxmlLoader.<laodingDialog>getController();
        Scene scene = new Scene(parent);
        LoadingDialog = new Stage();
        LoadingDialog.initModality(Modality.APPLICATION_MODAL);
        LoadingDialog.setScene(scene);
        LoadingDialog.setResizable(false);
        LoadingDialog.initStyle(StageStyle.UNDECORATED);
    }

    private void GetData(String data) {
        LoadingDialog.show();
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                HttpURLConnection connection = null;
                String line;
                StringBuffer responsecontent = new StringBuffer();
                BufferedReader reader;
                try {
                    URL url = new URL(URL_SITE + "/api/client/" + data);
                    connection = (HttpURLConnection) url.openConnection();
                    String encoded = Base64.getEncoder().encodeToString((credentials.getUsername() + ":" + credentials.getPassword()).getBytes(StandardCharsets.UTF_8));  //Java 8
                    connection.setRequestProperty("Authorization", "Basic " + encoded);
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);

                    int status = connection.getResponseCode();

                    if (status < 200) {
                        reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                        while ((line = reader.readLine()) != null) {
                            responsecontent.append(line);
                        }
                        reader.close();
                    } else {
                        reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        while ((line = reader.readLine()) != null) {
                            responsecontent.append(line);
                        }
                        reader.close();
                    }
                    JsonObject response = new Gson().fromJson(responsecontent.toString(), JsonObject.class);
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            int Status = response.get("status").getAsInt();
                            if (Status == 404) {
                                ShowDialog("Not Found", "User Not Found");
                            } else {
                                System.out.println("sdsadsad");
                                Client[] clientArrayList = new Gson().fromJson(response.get("msg"), Client[].class);
                                if (clientArrayList.length == 0) {
                                    ShowDialog("Not Found", "User Not Found");
                                    LoadingDialog.close();
                                    return;
                                }
                                ArrayList<User> data = new ArrayList<User>();
                                for (Client cli : clientArrayList) {
                                    data.add(new User(cli.getName(), cli.getCNIC(), cli.getEMAIL(), cli.getPHONE_NO()));
                                }
                                LoadingDialog.close();
                                SelectUser(data, clientArrayList);
                            }
                        }
                    });

                    connection.disconnect();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            ShowDialog("Connection Error", "PC isn't connected to the Internet");
                            LoadingDialog.close();
                        }
                    });


                }
            }
        });
        thread.start();
    }

    public void SelectUser(ArrayList<User> data, Client[] clientss) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TabelView.fxml"));
        Parent parent = null;
        try {
            parent = (Parent) fxmlLoader.load();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        TableViewDilaog tableViewDilaog = fxmlLoader.<TableViewDilaog>getController();
        tableViewDilaog.setTableContent(data);
        tableViewDilaog.setClickedRow(new ClickedRow() {
            @Override
            public void ClickedRowCNIC(String text) {
                for (Client cl : clientss) {
                    if (cl.getCNIC().equals(text)) {
                        client = cl;
                        ShowData();
                        break;
                    }
                }

            }
        });
        Scene scene = new Scene(parent);
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();
    }

    private void Validator(JFXTextField filed) {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        filed.getValidators().add(validator);
        filed.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                filed.validate();

            }
        });
    }

    private void Validator(JFXTextArea filed) {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        filed.getValidators().add(validator);
        filed.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) filed.validate();
        });
    }

    private void ShowDialog(String heading, String Text) {
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label(heading));
        layout.setBody(new Label(Text));
        dialog.setDialogContainer(stackpane);
        dialog.setContent(layout);
        dialog.setTransitionType(JFXDialog.DialogTransition.RIGHT);
        dialog.show();
    }

    @FXML
    private void OnSubmit(ActionEvent e) {
        if (validateAllFields()) {
            if (CurrentMethod.equals(POST)) {
                PostData();
            } else {
                PutData();
            }
        }
    }

    private void PutData() {
        LoadingDialog.show();
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {

                String charset = "UTF-8";
                String requestURL = URL_SITE + "/api/clientPUT/" + client.getId();

                MultipartUtility multipart = null;
                try {
                    multipart = new MultipartUtility(requestURL, charset, "PUT");
                    multipart.addFormField("Name", client.getName());
                    multipart.addFormField("CNIC", client.getCNIC());
                    multipart.addFormField("Gender", client.getGender());
                    multipart.addFormField("Father_Name", client.getFather_Name());
                    multipart.addFormField("Profession", client.getProfession());
                    multipart.addFormField("Address", client.getAddress());
                    multipart.addFormField("DOB", client.getDOB());
                    multipart.addFormField("EMAIL", client.getEMAIL());
                    multipart.addFormField("PHONE_NO", client.getPHONE_NO());
                    multipart.addFormField("Emg_Contact", client.getEmg_Contact());
                    multipart.addFormField("Emg_Name", client.getEmg_Name());
                    multipart.addFormField("Status", client.getStatus());
                    multipart.addFormField("Diseases", client.getDiseases());
                    multipart.addFormField("Finger_template", client.getFinger_template());
                    if (client.getID_PIC_1() != null)
                        multipart.addFilePart("ID_PIC", client.getID_PIC_1());
                    if (client.getID_PIC_2_File() != null)
                        multipart.addFilePart("ID_PIC_2", client.getID_PIC_2_File());
                    if (client.getDP_2() != null)
                        multipart.addFilePart("DP", client.getDP_2());

                    JsonObject response = new Gson().fromJson(multipart.finish(), JsonObject.class);
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            int Status = response.get("status").getAsInt();
                            if (Status == 200) {
                                EmptyAllFields();
                                String message = response.get("msg").getAsString();
                                ShowDialog("Client Updated", message);
                                RegisterClient();
                            } else if (Status == 400) {
                                String message = response.get("msg").getAsString();
                                ShowDialog("Error", message);
                            }
                            LoadingDialog.close();
                        }
                    });
                } catch (IOException e) {

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            LoadingDialog.close();
                            ShowDialog("Error", "Connection Error. Check Your internet connection");
                        }
                    });
                }
            }
        });
        thread.start();

    }

    private void PostData() {
        LoadingDialog.show();
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                String charset = "UTF-8";
                String requestURL = URL_SITE + "/api/ClientPost";
                MultipartUtility multipart = null;
                try {
                    multipart = new MultipartUtility(requestURL, charset);
                    multipart.addFormField("Name", client.getName());
                    multipart.addFormField("CNIC", client.getCNIC());
                    multipart.addFormField("Gender", client.getGender());
                    multipart.addFormField("Father_Name", client.getFather_Name());
                    multipart.addFormField("Profession", client.getProfession());
                    multipart.addFormField("Address", client.getAddress());
                    multipart.addFormField("DOB", client.getDOB());
                    multipart.addFormField("EMAIL", client.getEMAIL());
                    multipart.addFormField("PHONE_NO", client.getPHONE_NO());
                    multipart.addFormField("Emg_Contact", client.getEmg_Contact());
                    multipart.addFormField("Emg_Name", client.getEmg_Name());
                    multipart.addFormField("Status", "pending");
                    multipart.addFormField("Diseases", client.getDiseases());
                    multipart.addFormField("Finger_template", client.getFinger_template());
                    if (client.getID_PIC_1() != null)
                        multipart.addFilePart("ID_PIC", client.getID_PIC_1());
                    if (client.getID_PIC_2_File() != null)
                        multipart.addFilePart("ID_PIC_2", client.getID_PIC_2_File());
                    if (client.getDP_2() != null)
                        multipart.addFilePart("DP", client.getDP_2());
                    JsonObject response = new Gson().fromJson(multipart.finish(), JsonObject.class);
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            int Status = response.get("status").getAsInt();
                            if (Status == 200) {
                                EmptyAllFields();
                                String message = response.get("msg").getAsString();
                                ShowDialog("Client Added", message);
                            } else if (Status == 400) {
                                String message = response.get("msg").getAsString();
                                ShowDialog("Error", message);
                            }
                            LoadingDialog.close();
                        }
                    });
                } catch (IOException e) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            LoadingDialog.close();
                            ShowDialog("Error", "Connection Error. Check Your internet connection");
                        }
                    });
                }
            }
        });
        thread.start();


    }


    @FXML
    private void CaptureFingerPrint(ActionEvent e) {
        if (FingerprintSensorErrorCode.ZKFP_ERR_OK != FingerprintSensorEx.Init()) {
            ShowDialog("Sensor Error", "Fingerprint sensor not found");
            return;
        }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/FingerPrintDialog.fxml"));
        Parent parent = null;
        try {
            parent = (Parent) fxmlLoader.load();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        FingerPrintDialog dialogController = fxmlLoader.<FingerPrintDialog>getController();
        dialogController.setTemplateSetter(Controller.this);
        Scene scene = new Scene(parent);
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setOnHidden(se -> dialogController.shutdown());

        stage.showAndWait();
    }

    @Override
    public void SetTemplate(String string) {
        client.setFinger_template(string);
        File file = new File("fingerprint.bmp");
        Image image = new Image(file.toURI().toString());
        Fingerprint.setImage(image);
    }

    private static final String Idname = "Capture.jpeg";
    private static final String DpName = "Dp.jpeg";

    @FXML
    public void UploadDp() {
        File file = Uploader(DP);
        if (file != null)
            client.setDP_2(file);
    }

    @FXML
    public void CaptureDp() {
        client.setDP_2(caputrepicture(DpName, DP));
    }


    @FXML
    public void UploadIdCard() {
        File file = Uploader(IDcardImageback);
        if (file != null)
            client.setID_PIC_1(file);
    }

    @FXML
    public void IdCapture() {
        client.setID_PIC_1(caputrepicture(Idname, IDcardImageback));
    }

    @FXML
    public void UploadIdCardFront() {
        File file = Uploader(IDcardImageFront);
        if (file != null)
            client.setID_PIC_2_File(file);
    }

    @FXML
    public void IdCaptureFront() {
        client.setID_PIC_2_File(caputrepicture(Idname, IDcardImageFront));
    }

    private File Uploader(ImageView img) {
        path = null;
        FileChooser.ExtensionFilter imageFilter
                = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
        final FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(imageFilter);

        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            img.setImage(image);

        }

        return file;
    }


    private File caputrepicture(String name, ImageView image) {
        file = null;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/CaptureImageDialogBox.fxml"));
        Parent parent = null;
        try {
            parent = (Parent) fxmlLoader.load();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        CameraDialog cameraDialog = fxmlLoader.<CameraDialog>getController();
        cameraDialog.setFilename(name);
        cameraDialog.setImageSetter(new ImageSetter() {
            @Override
            public void setimage() {
                file = new File(name);
                Image im = new Image(file.toURI().toString());
                image.setImage(im);

            }

            @Override
            public void ErrorWebcame() {
                ShowDialog("Camera Error", "Please ensure that Camera is connected to the PC. :(");
            }
        });
        Scene scene = new Scene(parent);
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setOnHidden(se -> cameraDialog.shutdown());
        stage.showAndWait();

        return file;
    }

    @FXML
    public void AboutUs() {
        ShowDialog("Developer Contact",
                "Company   :  LunaTech\n" +
                        "Email          :  admin@luntech.pk\n" +
                        "Contact      :  (+92)3041053082 / (+92)3485667881");

    }


    boolean Validate_Date() {
        JFXTextField var = DOB;
        String value = var.getText().toString().trim();
        if (value.length() <= 0) {
            Validate_Dialog += "Date of Birth required\n";
            return false;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setLenient(false);
        try {
            format.parse(value);
            client.setDOB(value);
            return true;
        } catch (ParseException e) {
            Validate_Dialog += "Enter Date of Birth in  YYYY-MM-DD Format\n";
            return false;
        }
    }

    boolean Validate_DP() {
//        if (CurrentMethod.equals(POST)) {
//            if (client.getDP_2() == null) {
//                Validate_Dialog += "Profile Picture required\n";
//                return false;
//            }
//
//        }
        return true;
    }

    boolean Validate_ID_Card() {
//        if (CurrentMethod.equals(POST)) {
//
//            if (client.getID_PIC_1() == null) {
//                Validate_Dialog += "CNIC Picture required\n";
//                return false;
//            }
//        }
        return true;
    }

    boolean ValidateName() {
        JFXTextField var = Name;
        String value = var.getText().toString().trim();
        if (value.length() <= 0) {
            Validate_Dialog += "Client name required\n";
            return false;
        }

        client.setName(value);
        return true;
    }

    boolean ValiProfession() {
        JFXTextField var = Profession;
        String value = var.getText().toString().trim();
        if (value.length() <= 0) {
            Validate_Dialog += "Profession required\n";
            return false;
        }

        client.setProfession(value);
        return true;
    }

    boolean ValiAddress() {
        JFXTextArea var = Address;
        String value = var.getText().toString().trim();
        if (value.length() <= 0) {
            Validate_Dialog += "Address required\n";
            return false;
        }

        client.setAddress(value);
        return true;
    }

    boolean ValidateEmName() {
        JFXTextField var = EmName;
        String value = var.getText().toString().trim();
        if (value.length() <= 0) {
            Validate_Dialog += "Emergency name required\n";
            return false;
        }

        client.setEmg_Name(value);
        return true;
    }

    boolean ValidateIdCard() {
        JFXTextField var = IdCard;
        String value = var.getText().toString().trim();
        if (value.length() <= 0) {
            Validate_Dialog += "ID card no required\n";
            return false;
        }
        client.setCNIC(value);
        return true;
    }

    boolean ValidateFatherName() {
        JFXTextField var = FatherName;
        String value = var.getText().toString().trim();
        if (value.length() <= 0) {
            Validate_Dialog += "Father name required\n";
            return false;
        }

        client.setFather_Name(value);
        return true;
    }

    boolean ValidateEmail() {
        JFXTextField var = Email;
        String value = var.getText().toString().trim();
        if (value.length() <= 0) {
            Validate_Dialog += "Email required \n";
            return false;
        } else if (!Pattern.matches("[a-zA-Z0-9]{1,}[@]{1}[a-z]{5,}[.]{1}+[a-z]{3}", value)) {
            Validate_Dialog += "Enter a valid email address  \n";
            return false;
        }
        client.setEMAIL(value);
        return true;
    }

    boolean ValidatePhoneNumber() {
        JFXTextField var = PhoneNo;
        String value = var.getText().toString().trim();
        if (value.length() < 0) {
            Validate_Dialog += "Phone No. required \n";
            return false;
        } else if (value.length() != 11 || !Pattern.matches("[0-9]+", value)) {
            Validate_Dialog += "Wrong Number \n";
            return false;
        }
        client.setPHONE_NO(value);
        return true;
    }

    boolean ValidateEmrPhoneNumber() {
        JFXTextField var = EmContact;
        String value = var.getText().toString().trim();
        if (value.length() == 0) {
            Validate_Dialog += "Emergency  no required \n";
            return false;
        } else if (value.length() != 11 || !Pattern.matches("[0-9]+", value)) {
            Validate_Dialog += "Wrong Emergency number \n";
            return false;
        }
        client.setEmg_Contact(value);
        return true;
    }

    boolean ValidateGender() {
        if (Male.isSelected()) {
            client.setGender(Client.Male);
            return true;
        } else if (Female.isSelected()) {
            client.setGender(Client.Female);
            return true;
        }
        Validate_Dialog += "Select a Gender\n";
        return false;
    }

    void EmptyAllFields() {
        File file = new File("C:\\Users\\MMH\\Desktop\\JAVAFX\\src\\images\\jimmy-fallon.png");
        DP.setImage(new Image(file.toURI().toString()));
        IDcardImageback.setImage(null);
        IDcardImageFront.setImage(null);
        Fingerprint.setImage(null);
        Name.setText("");
        FatherName.setText("");
        IdCard.setText("");
        PhoneNo.setText("");
        DOB.setText("");
        Email.setText("");
        EmName.setText("");
        EmContact.setText("");
        Profession.setText("");
        Address.setText("");
        BloodPressure.setSelected(false);
        Asthama.setSelected(false);
        HeartDisease.setSelected(false);
        Arthritis.setSelected(false);
        Hepatitus.setSelected(false);
        Diabetes.setSelected(false);
        Male.setSelected(false);
        Female.setSelected(false);
        client.setID_PIC_1(null);
        client.setID_PIC_2(null);
        client.setID_PIC_2_File(null);
        client.setID_PIC(null);
        client.setFinger_template("");
        client.setDP_2(null);
    }

    void SelectedDiseases() {
        String value = "";
        if (BloodPressure.isSelected()) {
            value += "Blood Pressure,";
        }
        if (HeartDisease.isSelected()) {
            value += "Heart Disease,";
        }
        if (Diabetes.isSelected()) {
            value += "Diabetes,";
        }
        if (Hepatitus.isSelected()) {
            value += "Hepatitis,";
        }
        if (Asthama.isSelected()) {
            value += "Asthma,";
        }
        if (Arthritis.isSelected()) {
            value += "Arthritis";
        }
        if (value.length() == 0) {
            value = "None";
        }
        client.setDiseases(value);
    }

    boolean validateAllFields() {
        Validate_Dialog = "";
        SelectedDiseases();
        if (Validate_ID_Card() & Validate_DP() & ValidateGender() & ValiAddress() & ValidateEmrPhoneNumber() & Validate_Date() & ValidateEmail() & ValidateIdCard() & ValidateFatherName() & ValidateEmName() & ValidateName() & ValiProfession() & ValidatePhoneNumber()) {

            return true;
        }

        ShowDialog("Validation Errors", Validate_Dialog);
        return false;
    }


    private void ShowData() {
        if (null != client.getDP())
            DP.setImage(new Image(URL_SITE + client.getDP()));
        if (null != client.getID_PIC())
            IDcardImageback.setImage(new Image(URL_SITE + client.getID_PIC()));
        if (null != client.getID_PIC_2())
            IDcardImageFront.setImage(new Image(URL_SITE + client.getID_PIC_2()));

        Fingerprint.setImage(new Image(new File("C:\\Users\\MMH\\Desktop\\JAVAFX\\src\\images\\fingerprint.bmp").toURI().toString()));
        Name.setText(client.getName());
        FatherName.setText(client.getFather_Name());
        IdCard.setText(client.getCNIC());
        PhoneNo.setText(client.getPHONE_NO());
        DOB.setText(client.getDOB());
        Email.setText(client.getEMAIL());
        EmName.setText(client.getEmg_Name());
        EmContact.setText(client.getEmg_Contact());
        Profession.setText(client.getProfession());
        Address.setText(client.getAddress());
        String[] Deases = client.getDiseases().split(",");
        for (String de : Deases) {
            switch (de) {
                case "Blood Pressure" -> BloodPressure.setSelected(true);
                case "Heart Disease" -> HeartDisease.setSelected(true);
                case "Diabetes" -> Diabetes.setSelected(true);
                case "Hepatitis" -> Hepatitus.setSelected(true);
                case "Asthma" -> Asthama.setSelected(true);
                case "Arthritis" -> Arthritis.setSelected(true);
            }
        }
        if (client.getGender().equals(Client.Male)) {
            Male.setSelected(true);

            Female.setSelected(false);
        } else {
            Male.setSelected(false);
            Female.setSelected(true);
        }
        FormShowData(client);
        stackPaneSearchUpdate.setVisible(false);
        stackPaneSearchUpdate.managedProperty().bind(search.visibleProperty());
    }


    boolean ValDatePack() {
        JFXTextField var = PackageDate;
        String value = var.getText().toString().trim();
        if (value.length() > 0) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            format.setLenient(false);
            try {
                format.parse(value);
                client.setDOB(value);
                return true;
            } catch (ParseException e) {
                ShowDialog("Invalid", "Enter date in  YYYY-MM-DD Format\n");
                return false;
            }
        }

        return true;
    }

    private boolean CheckPacakge() {
        GymPakagetDetail profile = (GymPakagetDetail) Packagedetail.getValue();
        if (profile == null) {
            ShowDialog("Invalid", "Select a package first ");
            return false;
        }
        return true;
    }


    @FXML
    public void RenewPackage() {
        RenewPackag();
    }


    @FXML
    public void ResPrintRecipet() {
        if (ValDatePack() & (dataPackteNew != null)) {

            PrintableData data = new PrintableData();
            data.setAmount(Integer.parseInt(dataPackteNew.getAmount()));
            data.setMemberid(Integer.parseInt(dataPackteNew.getMemeber_Id()));
            data.setCompanyName(dataPackteNew.getCompanyName());
            data.setContacts(dataPackteNew.getContacts());
            data.setStartDate(dataPackteNew.getStartDate());
            data.setEndDate(dataPackteNew.getEndDate());
            data.setPackage(dataPackteNew.getCurrent_Package());
            data.setName(dataPackteNew.getPerson_Name());
            data.prindata();
        }
    }

    @FXML
    public void RestoreFingerPrint() {
        if (ValDatePack() & (dataPackteNew != null)) {
            LoadingDialog.show();
            if ((dataPackteNew.getStatus().toLowerCase()).equals("pending")) {
                ShowDialog("Error", "Client Account is not approved yet..\nYou can add fingerprint after after owner approves the client.");
                LoadingDialog.close();
                RestDataPackete();
            } else if ((dataPackteNew.getFigerPrint()).length() < 10) {
                ShowDialog("Error", "No fingerprint associated with this client. Update client data");
                LoadingDialog.close();
                RestDataPackete();

            } else {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            javaSocket.sendMessage(new SocketDataModel(dataPackteNew.getCNIC(), 500,
                                    dataPackteNew.getPerson_Name(), dataPackteNew.getFigerPrint()).toString());
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    LoadingDialog.close();
                                    RestDataPackete();
                                }
                            });
                        } catch (IOException e) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    LoadingDialog.close();
                                    RestDataPackete();
                                }
                            });
                            e.printStackTrace();
                        }
                    }
                }).start();

            }

        }
    }

    private void RenewPackag() {
        if (ValDatePack() & (dataPackteNew != null) & CheckPacakge()) {
            GymPakagetDetail id = (GymPakagetDetail) Packagedetail.getValue();
            String date = PackageDate.getText().toString().trim();
            LoadingDialog.show();
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {

                    String charset = "UTF-8";
                    String requestURL = URL_SITE + "/api/PostPackage";
                    MultipartUtility multipart = null;
                    try {
                        multipart = new MultipartUtility(requestURL, charset);
                        multipart.addFormField("id", String.valueOf(id.getId()));
                        multipart.addFormField("CNIC", dataPackteNew.getCNIC());
                        if (date.length() > 0)
                            multipart.addFormField("date", date);
                        else
                            multipart.addFormField("date", "");

                        JsonObject response = new Gson().fromJson(multipart.finish(), JsonObject.class);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                int Status = response.get("status").getAsInt();
                                if (Status == 200) {
                                    EmptyAllFields();
                                    String message = response.get("msg").getAsString();
                                    PrintableData data = new Gson().fromJson(message, PrintableData.class);
                                    data.prindata();
                                    if ((dataPackteNew.getStatus().toLowerCase()).equals("pending")) {
                                        ShowDialog("Package renewal error", "Client Account is not approved yet..\nYou can add fingerprint after after owner approves the client.");
                                        LoadingDialog.close();
                                        RestDataPackete();


                                    } else if ((dataPackteNew.getFigerPrint()).length() < 10) {
                                        ShowDialog("Package renewal error", "No fingerprint associated with this client. Update client data");
                                        LoadingDialog.close();
                                        RestDataPackete();

                                    } else {
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    javaSocket.sendMessage(new SocketDataModel(dataPackteNew.getCNIC(), 500,
                                                            dataPackteNew.getPerson_Name(), dataPackteNew.getFigerPrint()).toString());
                                                    Platform.runLater(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            LoadingDialog.close();
                                                            RestDataPackete();
                                                        }
                                                    });
                                                } catch (IOException e) {
                                                    Platform.runLater(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            LoadingDialog.close();
                                                            RestDataPackete();
                                                        }
                                                    });
                                                    e.printStackTrace();
                                                }
                                            }
                                        }).start();

                                    }
                                } else if (Status == 404) {
                                    String message = response.get("msg").getAsString();
                                    ShowDialog("Error", message);
                                    RestDataPackete();
                                    LoadingDialog.close();

                                }
                            }
                        });
                    } catch (IOException e) {

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                LoadingDialog.close();
                                ShowDialog("Error", "Connection Error. Check your internet Connection");
                            }
                        });
                    }
                }
            });
            thread.start();
        }
    }


    public void FormShowData(Client client) {
        label.setText("");
        stackpane.getChildren().add(FORMVBOX);
        if (null != client.getDP())
            FormImage.setImage(new Image(URL_SITE + client.getDP()));
        FormName.setText(client.getName());
        FormFatherName.setText(client.getFather_Name());
        FormCNIC.setText(client.getCNIC());
        FormCell.setText(client.getPHONE_NO());
        FormDOB.setText(client.getDOB());
        FormEMAIL.setText(client.getEMAIL());
        FormEmName.setText(client.getEmg_Name());
        FormEmContact.setText(client.getEmg_Contact());
        FormProfession.setText(client.getProfession());
        FormAddress.setText(client.getAddress());
        FormDiseases.setText(client.getDiseases());
        FormStatus.setText(client.getStatus());

    }

    @FXML
    public void RemoveFome() {
        stackpane.getChildren().remove(FORMVBOX);
        label.setText(Update);

        DP.setVisible(true);
        DpHBox.setVisible(true);
        MainVBox.setVisible(true);
    }
}
