package MyLayout.Model;

import javafx.beans.property.SimpleStringProperty;

public class User {
    SimpleStringProperty Name;
    SimpleStringProperty cnic;
    SimpleStringProperty email;
    SimpleStringProperty number;

    public User(String name, String CNIC, String EMAIL, String PHONE_NO) {
        this.Name = new SimpleStringProperty(name);
        this.cnic = new SimpleStringProperty(CNIC);
        this.email = new SimpleStringProperty(EMAIL);
        this.number = new SimpleStringProperty(PHONE_NO);
    }

    public String getName() {
        return Name.get();
    }

    public SimpleStringProperty nameProperty() {
        return Name;
    }

    public void setName(String name) {
        this.Name.set(name);
    }

    public String getCnic() {
        return cnic.get();
    }

    public SimpleStringProperty cnicProperty() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic.set(cnic);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getNumber() {
        return number.get();
    }

    public SimpleStringProperty numberProperty() {
        return number;
    }

    public void setNumber(String number) {
        this.number.set(number);
    }
}
