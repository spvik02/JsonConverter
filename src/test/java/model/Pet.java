package model;

public class Pet extends Animal{
    private String collarColor;

    public Pet(){}
    public Pet(String name, int age, String collarColor) {
        super(name, age);
        this.collarColor = collarColor;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "collarColor='" + collarColor + '\'' +
                '}';
    }
}
