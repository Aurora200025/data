package BinaryPile.test;

import BinaryPile.heap.BinaryHeap;
import BinaryPile.printer.BinaryTrees;

import java.util.Comparator;
import java.util.Random;

public class TopK {

    public static void main(String[] args) {
        Integer[] arr = new Integer[50];
        arr = test(arr);
    }

    static Integer[] test(Integer[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 100);
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]+"  ");
        }
        int k = 5;
        Integer[] data = new Integer[k];
        BinaryHeap<Integer> heap = new BinaryHeap<Integer>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        for (int i = 0; i < arr.length; i++) {
            if (heap.size() < k) {
                heap.add(arr[i]);
            }else {
                if (arr[i] > heap.get()) {
                    heap.replace(arr[i]);
                }
            }
        }
        for (int i = 0; i < heap.size(); i++) {
            data[i] = heap.get();
        }
        System.out.println();
        System.out.println();
        BinaryTrees.println(heap);
        return data;
    }
}
