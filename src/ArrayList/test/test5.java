package ArrayList.test;

import java.util.Scanner;

public class test5 {

    public static void main(String[] args) {
        int aCommodity, aPrice, bCommodity, bPrice;
        int sumPrice = 0;
        int aCount = 0, bCount = 0;
        Scanner input = new Scanner(System.in);
        while (input.hasNextInt()) {
            int count=0;
            aCommodity = input.nextInt();
            aPrice = input.nextInt();
            bCommodity = input.nextInt();
            bPrice = input.nextInt();
            int[] a = new int[aCommodity];
            int[] b = new int[bCommodity];
            int[] sortSum = new int[aCommodity * bCommodity];
            int minIndex = aCommodity > bCommodity ? aCommodity : bCommodity;
            for (int i = 1; i < minIndex; i++) {
                int temp = 0;
                for (int j = 0; j < i; j++) {
                    int olda = 1;
                    sumPrice += aPrice;
                    aCount++;
                    if (olda * aPrice >= 200) {
                        sumPrice -= 50;
                        olda = 1;
                    }
                    olda++;
                    for (int k = 0; k < i; k++) {
                        int oldb = 1;
                        sumPrice += bPrice;
                        bCount++;
                        if (oldb * bPrice >= 200) {
                            sumPrice -= 50;
                            oldb = 1;
                        }
                        oldb++;
                    }
                    if (aCount + bCount == 500) {
                        sortSum[j] = aCount * aCommodity + bCount * bCommodity;
                        a[j] = aCount;
                        b[j] = bCount;
                    }
                }
            }
            for (int s=sortSum.length - 1; s>0; s--) {
                for (int p=0; p<s; p++) {
                    if (sortSum[p] > sortSum[p + 1]) {
                        int temp = sortSum[p];
                        sortSum[p] = sortSum[p + 1];
                        sortSum[p + 1] = temp;
                        count = p;
                    }
                }
            }
            System.out.println(a[count]+"  "+b[count]);
        }
    }
}
