package test;

public class leetcode865 {

    public int scoreOfParentheses(String string) {
        if(string == null || string.length() == 0) return 0;

        int res = 0;
        int size = -1;   // 相当于层数
        for(int i = 0 ; i < string.length() ; i ++){
            char c = string.charAt(i);
            if(c == '('){
                size ++;
            }else if(c == ')' && i > 0 && string.charAt(i - 1) == '('){
                res += Math.pow(2,size);
                size --;
            }else
                size --;
        }
        return res;
    }
}
