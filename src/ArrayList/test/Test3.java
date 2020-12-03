package ArrayList.test;

import java.util.Scanner;

public class Test3 {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String string = input.nextLine();
        int number = string.length();
        char chr[] = new char[number];
        StringBuilder newString = new StringBuilder("");

        for (int i = 0; i < number; i++) {
            chr[i] = string.charAt(i);
        }
        char temp = chr[0];
        newString.append(temp);
        for (int i = 1; i < number; i++) {
            newString.append(chr[i]);
            if (temp == chr[i]) {
                break;
            }
        }
        System.out.println(newString.toString());
    }
}
