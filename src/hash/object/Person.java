package hash.object;

import java.util.Objects;

public class Person {

    private Integer age;
    private float height;
    private String name;
    private Car car;

    public Person() {}
    public Person(Integer age, float height, String name) {
        this.age = age;
        this.height = height;
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return Float.compare(person.getHeight(), getHeight()) == 0 &&
                getAge().equals(person.getAge()) &&
                getName().equals(person.getName());
    }

    @Override
    public int hashCode() {
        int hashCode = Integer.hashCode(age);
        hashCode = hashCode * 31 + Float.hashCode(height);
        hashCode = hashCode * 31 + (name != null ? name.hashCode() : 0);
        hashCode = hashCode * 31 + (car != null ? car.hashCode() : 0);
        return hashCode;
    }
}
