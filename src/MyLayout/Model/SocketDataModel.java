package MyLayout.Model;

import com.google.gson.Gson;

public class SocketDataModel {
    private String id;
    private int Status;
    private String Name;
    private String template;


    public SocketDataModel(int id, int status, String name, String template) {
        this.id = String.valueOf(id);
        Status = status;
        Name = name;
        this.template = template;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
