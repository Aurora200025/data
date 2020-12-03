package ArrayList.test;

import java.util.Scanner;

public class test4 {
    public static void main(String[] args) {

        int a[], b[];
        Scanner input = new Scanner(System.in);
        while (input.hasNextInt()) {
            int n = input.nextInt();
            a = new int[n];
            b = new int[n];
            int aSum=0, aCount=0, bSum=0, bCount=0;
            for (int i = 0; i < n; i++) {
                a[i] = input.nextInt();
            }
            for (int i = 0; i < n; i++) {
                b[i] = input.nextInt();
            }
            for (int i = 0; i < n; i++) {
                if (a[i] > 0) {
                    aCount++;
                    aSum += 3;
                }else if (a[i] == 0) {
                    aSum += 1;
                }else {
                    aSum += 0;
                }
                if (b[i] > 0) {
                    bCount++;
                    bSum += 3;
                }else if (b[i] == 0) {
                    bSum += 1;
                }else {
                    bSum += 0;
                }
            }
            if (aSum > bSum) {
                System.out.println("A");
            } else if (aSum < bSum) {
                System.out.println("B");
            }else {
                if (aCount > bCount) {
                    System.out.println("A");
                } else if (aCount < bCount) {
                    System.out.println("B");
                }else {
                    System.out.println("Draw");
                }
            }
        }
    }
}
