package MyLayout.Model;

import MyLayout.DitheringSample;

public class PrintableData {
    private int  Memberid;
    private int  Amount;
    private String  Name;
    private String  StartDate;
    private String  EndDate;
    private String  Package;
    private String  CompanyName;
    private String  Contacts;

    public PrintableData(int memberid, int amount, String name, String startDate, String endDate, String aPackage, String companyName, String contacts) {
        Memberid = memberid;
        Amount = amount;
        Name = name;
        StartDate = startDate;
        EndDate = endDate;
        Package = aPackage;
        CompanyName = companyName;
        Contacts = contacts;
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

    public void prindata(){
        DitheringSample obj = new DitheringSample("BC-97AC");
        obj.print(this);
    }

    public PrintableData(){}
    public PrintableData(int memberid, int amount, String name, String startDate, String endDate, String aPackage) {
        Memberid = memberid;
        Amount = amount;
        Name = name;
        StartDate = startDate;
        EndDate = endDate;
        Package = aPackage;
    }

    @Override
    public String toString() {
        return "PrintableData{" +
                "Memberid=" + Memberid +
                ", Amount=" + Amount +
                ", Name='" + Name + '\'' +
                ", StartDate='" + StartDate + '\'' +
                ", EndDate='" + EndDate + '\'' +
                ", Package='" + Package + '\'' +
                '}';
    }

    public int getMemberid() {
        return Memberid;
    }

    public void setMemberid(int memberid) {
        Memberid = memberid;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
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

    public String getPackage() {
        return Package;
    }

    public void setPackage(String aPackage) {
        Package = aPackage;
    }
}
