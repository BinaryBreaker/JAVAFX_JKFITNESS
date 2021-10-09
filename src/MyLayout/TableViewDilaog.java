package MyLayout;


import MyLayout.Model.User;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import home.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TableViewDilaog implements Initializable {

    TableView<User> tableView;
    TableColumn<User, String> Name;
    TableColumn<User, String> Email;
    TableColumn<User, String> CNIC;
    TableColumn<User, String> Phone_No;
    ObservableList<User> data;
    @FXML
    VBox MAIN;

    ClickedRow createRow;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableView = new TableView<User>();
        Name = new TableColumn("Name");
        Name.prefWidthProperty().bind(tableView.widthProperty().divide(4));
        Email = new TableColumn("Email");
        Email.prefWidthProperty().bind(tableView.widthProperty().divide(4));
        CNIC = new TableColumn("CNIC");
        CNIC.prefWidthProperty().bind(tableView.widthProperty().divide(4));
        Phone_No = new TableColumn("Phone no");
        Phone_No.prefWidthProperty().bind(tableView.widthProperty().divide(4));

    }

    public void setTableContent(ArrayList<User> user) {
        data = FXCollections.observableArrayList(user);
        Name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        Email.setCellValueFactory(new PropertyValueFactory<>("email"));
        CNIC.setCellValueFactory(new PropertyValueFactory<>("cnic"));
        Phone_No.setCellValueFactory(new PropertyValueFactory<>("number"));
        tableView.setItems(data);
        tableView.getColumns().addAll(Name, Email,  Phone_No,CNIC);
        MAIN.getChildren().addAll(tableView);

        tableView.setRowFactory(tv -> {
            TableRow<User> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    User rowData = row.getItem();
                    createRow.ClickedRowCNIC(rowData.getCnic());
                    Stage selec = (Stage) MAIN.getScene().getWindow();
                    selec.close();
                }
            });
            return row;
        });
    }

    public void setClickedRow(ClickedRow clicked) {
        this.createRow = clicked;
    }


}

interface ClickedRow {
    public void ClickedRowCNIC(String text);
}
