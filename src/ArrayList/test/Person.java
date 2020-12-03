package ArrayList.test;

public class Person {

    private Integer pid;
    private String pname;

    public Person() {}

    public Person(Integer pid, String pname) {
        this.pid = pid;
        this.pname = pname;
    }

    @Override
    public String toString() {
        return "Person{" +
                "pid=" + pid +
                ", pname='" + pname + '\'' +
                '}';
    }
}
