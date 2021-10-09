package MyLayout.Model;

public class GymPakagetDetail {
    private int id;
    private String Name;
    private int Price;
    private int No_0f_Days;
    private boolean Status;

    public GymPakagetDetail(){}

    public GymPakagetDetail(int id, String name, int price, int no_0f_Days, boolean status) {
        this.id = id;
        Name = name;
        Price = price;
        No_0f_Days = no_0f_Days;
        Status = status;
    }

    @Override
    public String toString() {
        return "Package " + Name + "  |  " +"Price " + Price+ "  |  " + "No of Days " + No_0f_Days;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getNo_0f_Days() {
        return No_0f_Days;
    }

    public void setNo_0f_Days(int no_0f_Days) {
        No_0f_Days = no_0f_Days;
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }


}

