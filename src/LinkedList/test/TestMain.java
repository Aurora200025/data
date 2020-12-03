package LinkedList.test;


import LinkedList.LinkedList;
import LinkedList.SigLinkedList;

public class TestMain {

    public static void main(String[] args) {

        LinkedList<Integer> list = new LinkedList<Integer>();
        list.add(11);
        list.add(22);
        list.add(33);
        list.add(44);
        list.add(0, 55);
        list.add(2, 66);
        list.add(list.size(), 77);
        list.remove(0);
        list.remove(2);
        list.remove(list.size() - 1);

        System.out.println(list.toString());
//        SigLinkedList<Integer> list = new SigLinkedList<Integer>();
//        list.add(1);
//        list.add(2);
//        list.add(3);
//        list.add(1, 40);
//        System.out.println(list.toString());
    }
}
