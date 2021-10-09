package MyLayout.Model;

import java.io.File;

public class Client {

    public static final String Male = "Male";
    public static final String Female = "Female";
    private String Name;
    private int id;
    private String CNIC;
    private String Gender;
    private String Father_Name;
    private String Profession;
    private String Address;
    private String DOB;
    private String EMAIL;
    private String PHONE_NO;
    private String Emg_Contact;
    private String Emg_Name;
    private String Status;
    private String Finger_template;
    private File ID_PIC_1;
    private File ID_PIC_2;
    private File DP_2;
    private String ID_PIC;
    private String DP;
    private String Diseases;
    private String Finger_Print_Status;

    public File getID_PIC_2() {
        return ID_PIC_2;
    }

    public void setID_PIC_2(File ID_PIC_2) {
        this.ID_PIC_2 = ID_PIC_2;
    }

    public String getID_PIC() {
        return ID_PIC;
    }

    public void setID_PIC(String ID_PIC) {
        this.ID_PIC = ID_PIC;
    }

    public String getDP() {
        return DP;
    }

    public void setDP(String DP) {
        this.DP = DP;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client() {
    }

    public Client(String name, String CNIC, String EMAIL, String PHONE_NO) {
        Name = name;
        this.CNIC = CNIC;
        this.EMAIL = EMAIL;
        this.PHONE_NO = PHONE_NO;
    }

    public Client(String name, String CNIC, String gender, String father_Name, String profession, String address, String DOB, String EMAIL, String PHONE_NO, String emg_Contact, String emg_Name, String status, String finger_template, File ID_PIC_1, File DP_2, String diseases, String finger_Print_Status) {
        Name = name;
        this.CNIC = CNIC;
        Gender = gender;
        Father_Name = father_Name;
        Profession = profession;
        Address = address;
        this.DOB = DOB;
        this.EMAIL = EMAIL;
        this.PHONE_NO = PHONE_NO;
        Emg_Contact = emg_Contact;
        Emg_Name = emg_Name;
        Status = status;
        Finger_template = finger_template;
        this.ID_PIC_1 = ID_PIC_1;
        this.DP_2 = DP_2;
        Diseases = diseases;
        Finger_Print_Status = finger_Print_Status;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCNIC() {
        return CNIC;
    }

    public void setCNIC(String CNIC) {
        this.CNIC = CNIC;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getFather_Name() {
        return Father_Name;
    }

    public void setFather_Name(String father_Name) {
        Father_Name = father_Name;
    }

    public String getProfession() {
        return Profession;
    }

    public void setProfession(String profession) {
        Profession = profession;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getPHONE_NO() {
        return PHONE_NO;
    }

    public void setPHONE_NO(String PHONE_NO) {
        this.PHONE_NO = PHONE_NO;
    }

    public String getEmg_Contact() {
        return Emg_Contact;
    }

    public void setEmg_Contact(String emg_Contact) {
        Emg_Contact = emg_Contact;
    }

    public String getEmg_Name() {
        return Emg_Name;
    }

    public void setEmg_Name(String emg_Name) {
        Emg_Name = emg_Name;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getFinger_template() {
        return Finger_template;
    }

    public void setFinger_template(String finger_template) {
        Finger_template = finger_template;
    }

    public File getID_PIC_1() {
        return ID_PIC_1;
    }

    public void setID_PIC_1(File ID_PIC_1) {
        this.ID_PIC_1 = ID_PIC_1;
    }

    public File getDP_2() {
        return DP_2;
    }

    public void setDP_2(File DP_2) {
        this.DP_2 = DP_2;
    }

    public String getDiseases() {
        return Diseases;
    }

    public void setDiseases(String diseases) {
        Diseases = diseases;
    }

    public String getFinger_Print_Status() {
        return Finger_Print_Status;
    }

    public void setFinger_Print_Status(String finger_Print_Status) {
        Finger_Print_Status = finger_Print_Status;
    }

    @Override
    public String toString() {
        return "Client{" +
                "Name='" + Name + '\'' +
                ", id=" + id +
                ", CNIC='" + CNIC + '\'' +
                ", Gender='" + Gender + '\'' +
                ", Father_Name='" + Father_Name + '\'' +
                ", Profession='" + Profession + '\'' +
                ", Address='" + Address + '\'' +
                ", DOB='" + DOB + '\'' +
                ", EMAIL='" + EMAIL + '\'' +
                ", PHONE_NO='" + PHONE_NO + '\'' +
                ", Emg_Contact='" + Emg_Contact + '\'' +
                ", Emg_Name='" + Emg_Name + '\'' +
                ", Status='" + Status + '\'' +
                ", Finger_template='" + Finger_template + '\'' +
                ", ID_PIC_1=" + ID_PIC_1 +
                ", DP_2=" + DP_2 +
                ", ID_PIC='" + ID_PIC + '\'' +
                ", DP='" + DP + '\'' +
                ", Diseases='" + Diseases + '\'' +
                ", Finger_Print_Status='" + Finger_Print_Status + '\'' +
                '}';
    }


}

