package priorityQueue.test;


/**
 * @author Aurora
 */
public class Person implements Comparable<Person> {

    private String name;
    private int boneBreak;

    public Person() {}
    public Person(String name, int boneBreak) {
        this.name = name;
        this.boneBreak = boneBreak;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBoneBreak() {
        return boneBreak;
    }

    public void setBoneBreak(int boneBreak) {
        this.boneBreak = boneBreak;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", boneBreak=" + boneBreak +
                '}';
    }

    @Override
    public int compareTo(Person o) {
        return this.getBoneBreak() - o.getBoneBreak();
    }
}
