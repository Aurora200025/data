package ArrayList.test;

import com.sun.corba.se.spi.orbutil.threadpool.NoSuchThreadPoolException;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int N = input.nextInt();
        int count = 0;
        for (int i = 2; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (Math.pow(j, 3) == i && (i % (Math.sqrt(i)) == 0)) {
                    count++;
                    break;
                }
            }
        }
        System.out.println(count);
    }
}
