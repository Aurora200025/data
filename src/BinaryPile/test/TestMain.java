package BinaryPile.test;

import BinaryPile.heap.BinaryHeap;
import BinaryPile.printer.BinaryTreeInfo;
import BinaryPile.printer.BinaryTrees;

public class TestMain {

    public static void main(String[] args) {
        Integer[] data = {88, 44, 53, 41, 16, 6, 70,
                18, 85, 98, 81, 23, 36, 43, 37};
        BinaryHeap<Integer> heap = new BinaryHeap<Integer>(data);
        BinaryTrees.println(heap);
        data[0] = 10;
        data[1] = 20;
        BinaryTrees.println(heap);
    }
}
