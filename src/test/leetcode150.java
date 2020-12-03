package test;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class leetcode150 {

    public static void main(String[] args) {
        String[] str = new String[]{"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"};
        System.out.println(evalRPN(str));
    }
    public static int evalRPN(String[] tokens) {
        int prev, next;
        Stack<Integer> stack = new Stack<>();
        for (int i=0; i<tokens.length; i++) {
            String string = tokens[i];
            if (string.equals("*")) {
                prev = stack.pop();
                next = stack.pop();
                stack.push(next * prev);
            } else if (string.equals("/")) {
                prev = stack.pop();
                next = stack.pop();
                stack.push(next / prev);
            }else if (string.equals("+")) {
                prev = stack.pop();
                next = stack.pop();
                stack.push(next + prev);
            }else if (string.equals("-")) {
                prev = stack.pop();
                next = stack.pop();
                stack.push(next - prev);
            }else {
                stack.push(Integer.valueOf(string));
            }
        }
        return stack.pop();
    }
}
