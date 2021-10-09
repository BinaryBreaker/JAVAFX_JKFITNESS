package FA19_BCS_191_5C_MUHAMMAD_MUZAMIL_HUSSAIN;

public class Question1 {
    public static void main(String[] args) {

        Trigon one = new Trigon();
        one.setShapeType("90");
        one.setBase(1.0);
        one.setPerpendicular(2.0);
        one.setHypotenuse(3.0);
        System.out.println("Base of 90 :: " + one.getBase());
        System.out.println("perpendicular of 90 :: " + one.getPerpendicular());
        System.out.println("hypotenuse of 90 :: " + one.getHypotenuse());
        one.displayArea();

        System.out.println("\n\n");
        Trigon Two = new Trigon(2.0, 3.0, 4.0, "50");
        System.out.println("Base of 50 :: " + Two.getBase());
        System.out.println("perpendicular of 50 :: " + Two.getPerpendicular());
        System.out.println("hypotenuse of 50 :: " + Two.getHypotenuse());
        Two.displayArea();

        System.out.println("\n\n");
        Trigon three = new Trigon(8.0, 4.0, 4.0, "60");
        System.out.println("Base of 60 :: " + three.getBase());
        System.out.println("perpendicular of 60 :: " + three.getPerpendicular());
        System.out.println("hypotenuse of 60 :: " + three.getHypotenuse());
        Two.displayArea();
    }
}

class GeometricShape {
    protected String ShapeType;

    public void setShapeType(String shapeType) {
        ShapeType = shapeType;
    }
}

class Trigon extends GeometricShape {
    private double base;
    private double perpendicular;
    private double hypotenuse;

    public Trigon() {
        this.base = 1.0;
        this.hypotenuse = 1.0;
        this.perpendicular = 1.0;
    }

    public Trigon(double base, double perpendicular, double hypotenuse, String ShapeType) {
        this.base = base;
        this.perpendicular = perpendicular;
        this.hypotenuse = hypotenuse;
        this.ShapeType = ShapeType;
    }

    public void setBase(double base) {
        this.base = base;
    }

    public void setPerpendicular(double perpendicular) {
        this.perpendicular = perpendicular;
    }

    public void setHypotenuse(double hypotenuse) {
        this.hypotenuse = hypotenuse;
    }

    public double getBase() {
        return base;
    }

    public double getPerpendicular() {
        return perpendicular;
    }

    public double getHypotenuse() {
        return hypotenuse;
    }

    public void displayArea() {
        System.out.println("Area Of " + this.ShapeType + " :: " + (getHypotenuse() + getBase() + getPerpendicular()));
    }
}