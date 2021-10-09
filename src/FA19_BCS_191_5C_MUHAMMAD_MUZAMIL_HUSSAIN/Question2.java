package FA19_BCS_191_5C_MUHAMMAD_MUZAMIL_HUSSAIN;

public class Question2 {

    public static void main(String[] args) {
        Person stu = new Student(
                "Muhammad Muzamil Hussain",
                "18",
                "3820211155",
                "ghori town",
                "FA19-BCS-191|5C",
                "5C",
                100
        );
        stu.Behavior();
        Person teacher = new Teacher(
                "Muhammad Muzamil Hussain",
                "18",
                "3820211155",
                "ghori town",
                "15616",
                "1500"
        );
        teacher.Behavior();


    }
}


class Student extends Person {
    private String registrationID;
    private String department;
    private int numberOfCompletedCreditHours;

    public Student() {
    }

    @Override
    void Behavior() {
        System.out.println("\n\n");

        System.out.println("Name :: " + this.getName());
        System.out.println("age :: " + this.getAge());
        System.out.println("number :: " + this.getPhone());
        System.out.println("address :: " + this.getAddress());
        System.out.println("registrationID :: " + this.getRegistrationID());
        System.out.println("department :: " + this.getDepartment());
        System.out.println("numberOfCompletedCreditHours :: " + this.getNumberOfCompletedCreditHours());
        System.out.println("pay course registration fee");
    }

    public Student(String registrationID, String department, int numberOfCompletedCreditHours) {
        this.registrationID = registrationID;
        this.department = department;
        this.numberOfCompletedCreditHours = numberOfCompletedCreditHours;
    }

    public Student(String name, String age, String number, String address, String registrationID, String department, int numberOfCompletedCreditHours) {
        super(name, age, number, address);
        this.registrationID = registrationID;
        this.department = department;
        this.numberOfCompletedCreditHours = numberOfCompletedCreditHours;
    }

    public String getRegistrationID() {
        return registrationID;
    }

    public void setRegistrationID(String registrationID) {
        this.registrationID = registrationID;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getNumberOfCompletedCreditHours() {
        return numberOfCompletedCreditHours;
    }

    public void setNumberOfCompletedCreditHours(int numberOfCompletedCreditHours) {
        this.numberOfCompletedCreditHours = numberOfCompletedCreditHours;
    }
}

class Teacher extends Person {
    private String staffID;
    private String salary;

    public Teacher() {
    }

    @Override
    void Behavior() {
        System.out.println("\n\n");
        System.out.println("Name :: " + this.getName());
        System.out.println("age :: " + this.getAge());
        System.out.println("number :: " + this.getPhone());
        System.out.println("address :: " + this.getAddress());
        System.out.println("staffID :: " + this.getStaffID());
        System.out.println("salary :: " + this.getSalary());
        System.out.println("teach course(s)");
    }

    public Teacher(String staffID, String salary) {
        this.staffID = staffID;
        this.salary = salary;
    }

    public Teacher(String name, String age, String number, String address, String staffID, String salary) {
        super(name, age, number, address);
        this.staffID = staffID;
        this.salary = salary;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }
}


abstract class Person {
    protected String name;
    protected String age;
    protected String phone;
    protected String address;

    public Person() {
    }

    abstract void Behavior();

    public Person(String name, String age, String phone, String address) {
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}