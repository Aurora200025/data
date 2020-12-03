package test;

import java.util.Stack;

public class leetcode20 {

//    public boolean isValid(String s) {
////        while (s.contains("{}") ||
////               s.contains("[]") ||
////               s.contains("()")) {
////            s = s.replace("{}", "");
////            s = s.replace("[]", "");
////            s = s.replace("()", "");
////        }
////        return s.isEmpty();
//    }
    public boolean isValid(String s) {
        int num = s.length();
        Stack<Character> stack = new Stack<>();
        for (int i=0; i<num; i++) {
            char c = s.charAt(i);
            if (c == '[' || c == '{' || c == '(') {
                stack.push(c);
            }else {
                if (stack.isEmpty()) {
                    return false;
                }
                char left = stack.pop();
                if (left == '(' && c != ')') {
                    return false;
                }
                if (left == '{' && c != '}') {
                    return false;
                }if (left == '[' && c != ']') {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }
}
