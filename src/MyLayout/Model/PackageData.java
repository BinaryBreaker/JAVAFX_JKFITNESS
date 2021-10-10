package MyLayout.Model;

import java.util.ArrayList;

public class PackageData {
    private String Memeber_Id;
    private String Status;
    private String FigerPrint;
    private String Person_Name;
    private String Phone_No;
    private String CNIC;
    private String StartDate;
    private String EndDate;
    private String Current_Package;
    private String Amount;
    private String Dp_Pic;
    private String Email;
    private String CompanyName;
    private String Contacts;
    private ArrayList<GymPakagetDetail> Available_Packages;


    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public PackageData() {
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getContacts() {
        return Contacts;
    }

    public void setContacts(String contacts) {
        Contacts = contacts;
    }

    public PackageData(String memeber_Id, String status, String figerPrint, String person_Name, String phone_No, String CNIC, String startDate, String endDate, String current_Package, String amount, String dp_Pic, ArrayList<GymPakagetDetail> available_Packages) {
        Memeber_Id = memeber_Id;
        Status = status;
        FigerPrint = figerPrint;
        Person_Name = person_Name;
        Phone_No = phone_No;
        this.CNIC = CNIC;
        StartDate = startDate;
        EndDate = endDate;
        Current_Package = current_Package;
        Amount = amount;
        Dp_Pic = dp_Pic;
        Available_Packages = available_Packages;
    }

    public String getMemeber_Id() {
        return Memeber_Id;
    }

    public void setMemeber_Id(String memeber_Id) {
        Memeber_Id = memeber_Id;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getFigerPrint() {
        return FigerPrint;
    }

    public void setFigerPrint(String figerPrint) {
        FigerPrint = figerPrint;
    }

    public ArrayList<GymPakagetDetail> getAvailable_Packages() {
        return Available_Packages;
    }

    public void setAvailable_Packages(ArrayList<GymPakagetDetail> available_Packages) {
        Available_Packages = available_Packages;
    }

    public String getDp_Pic() {
        return Dp_Pic;
    }

    public void setDp_Pic(String dp_Pic) {
        Dp_Pic = dp_Pic;
    }


    @Override
    public String toString() {
        return "PackageData{" +
                "Person_Name='" + Person_Name + '\'' +
                ", Phone_No='" + Phone_No + '\'' +
                ", CNIC='" + CNIC + '\'' +
                ", StartDate='" + StartDate + '\'' +
                ", EndDate='" + EndDate + '\'' +
                ", Current_Package='" + Current_Package + '\'' +
                ", Amount='" + Amount + '\'' +
                ", Dp_Pic='" + Dp_Pic + '\'' +
                ", Available_Packages=" + Available_Packages +
                '}';
    }

    public String getPerson_Name() {
        return Person_Name;
    }

    public void setPerson_Name(String person_Name) {
        Person_Name = person_Name;
    }

    public String getPhone_No() {
        return Phone_No;
    }

    public void setPhone_No(String phone_No) {
        Phone_No = phone_No;
    }

    public String getCNIC() {
        return CNIC;
    }

    public void setCNIC(String CNIC) {
        this.CNIC = CNIC;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getCurrent_Package() {
        return Current_Package;
    }

    public void setCurrent_Package(String current_Package) {
        Current_Package = current_Package;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }
}
