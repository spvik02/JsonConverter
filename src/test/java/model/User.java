package model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class User {
    private String name;
    private int age;
    private Integer year;
    private List<Animal> pets;
    private Animal[] animals;
    private boolean isHappy;
    private Boolean isMarried;
    private char category;
    private Character categoryWrapper;

    public User() {
    }

    public User(String name, int age, Integer year, List<Animal> pets, Animal[] animals, boolean isHappy, Boolean isMarried, char category, Character categoryWrapper) {
        this.name = name;
        this.age = age;
        this.year = year;
        this.pets = pets;
        this.animals = animals;
        this.isHappy = isHappy;
        this.isMarried = isMarried;
        this.category = category;
        this.categoryWrapper = categoryWrapper;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Animal> getPets() {
        return pets;
    }

    public void setPets(List<Animal> pets) {
        this.pets = pets;
    }

    public Animal[] getAnimals() {
        return animals;
    }

    public void setAnimals(Animal[] animals) {
        this.animals = animals;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", year=" + year +
                ", pets=" + pets +
                ", animals=" + Arrays.toString(animals) +
                ", isHappy=" + isHappy +
                ", isMarried=" + isMarried +
                ", category=" + category +
                ", categoryWrapper=" + categoryWrapper +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (age != user.age) return false;
        if (isHappy != user.isHappy) return false;
        if (category != user.category) return false;
        if (!name.equals(user.name)) return false;
        if (!year.equals(user.year)) return false;
        if (!Objects.equals(pets, user.pets)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(animals, user.animals)) return false;
        if (!isMarried.equals(user.isMarried)) return false;
        return Objects.equals(categoryWrapper, user.categoryWrapper);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + age;
        result = 31 * result + year.hashCode();
        result = 31 * result + isMarried.hashCode();
        result = 31 * result + (int) category;
        result = 31 * result + (categoryWrapper != null ? categoryWrapper.hashCode() : 0);
        return result;
    }
}
