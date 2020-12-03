package ArrayList.test;

public class test6 {

    public static void main(String[] args) {
        String str = "a hello1 abc good!";
        String string = reserves(str);
        System.out.println(string);
    }

    public static String reserves(String string) {
        String[] strs = string.split(" ");
        int length = strs.length;
        if (length <= 1) {
            return string;
        }
        int left = 0, right = 0;
        while (right < length) {
            while (right < length) {
                right++;
            }
            int next = right + 1;
            right--;
            while (left < right) {
                String temp = strs[left];
                strs[left] = strs[right];
                strs[right] = temp;
                left++;
                right--;
            }
            left = next;
            right = next;
        }
        StringBuilder builder = new StringBuilder();
        for (int j = 0; j < length; j++) {
            if (j == length - 1) {
                builder.append("");
            }
            builder.append(strs[j]);
            builder.append(" ");
        }
        return builder.toString();
    }
}
