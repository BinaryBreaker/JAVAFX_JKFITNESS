package MyLayout.Model;

import com.google.gson.Gson;

public class SocketDataModel {
    private String cnic;
    private int Status;
    private String Name;
    private String template;


    public SocketDataModel(String cnic, int status, String name, String template) {
        this.cnic = cnic;
        Status = status;
        Name = name;
        this.template = template;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
