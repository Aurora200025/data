package ArrayList.test;


import ArrayList.ArrayList;

public class TestMain {

    public static void main(String[] args) {
        int[] array = new int[]{11, 12, 13};
        ArrayList<Person> list = new ArrayList<>();
        list.add(new Person(1, "zzh"));
        list.add(new Person(2, "zzt"));
        list.add(null);
        System.out.println(list.toString());
        System.out.println(list.size());
////        list.get(1);
//        list.add(1);
//        list.add(2);
//        list.add(3);
//        list.add(4);
//        list.add(5);
//        list.set(3, 80);
//        Asserts.test(list.get(3) == 80);
//        System.out.println(list.toString());
    }
}
