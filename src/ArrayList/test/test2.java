package ArrayList.test;

import java.util.Scanner;

public class test2 {
        public static void main(String[] args) {
            Scanner input = new Scanner(System.in);
            while (input.hasNextInt()){
                int num = input.nextInt();
                int count = 0;
                for (int i = 2; i <= num; i++) {
                    for (int j = 2; j < Math.sqrt(i); j++) {
                        if (i % j == 0) {
                            count++;
                            break;
                        }
                    }
                }
                System.out.println(count);
            }
        }
}
