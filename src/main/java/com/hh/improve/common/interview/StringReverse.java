package com.hh.improve.common.interview;

import java.util.Stack;

/**
 * 字符串反转函数的实现方法
 *
 * @author liuwh
 * @since 2015-06-27
 *
 */
public class StringReverse {

    /**
     *方法一：（利用递归实现）
     * @param s
     * @return
     */
    public static String reverse1(String s) {
        int length = s.length();
        if (length <= 1)
            return s;
        String left = s.substring(0, length / 2);
        String right = s.substring(length / 2, length);
        return reverse1(right) + reverse1(left);  //调用递归
    }

    /**
     *方法二：（拼接字符串）
     * @param s
     * @return
     */
    public static String reverse2(String s) {
        int length = s.length();
        String reverse = "";
        for (int i = 0; i < length; i++)
            reverse = s.charAt(i) + reverse;
        return reverse;
    }

    /**
     *方法三：（利用数组，倒序输出）
     * @param s
     * @return
     */
    public static String reverse3(String s) {
        char[] array = s.toCharArray();
        String reverse = "";
        for (int i = array.length - 1; i >= 0; i--)
            reverse += array[i];
        return reverse;
    }

    /**
     * 方法四：（利用StringBuffer的内置reverse方法）
     * @param s
     * @return
     */
    public static String reverse4(String s) {
        return new StringBuffer(s).reverse().toString();
    }

    /**
     * 方法五：（利用临时变量，交换两头数值）
     * @param s
     * @return
     */
    public static String reverse5(String orig) {
        char[] s = orig.toCharArray();
        int n = s.length - 1;
        int halfLength = n / 2;
        for (int i = 0; i <= halfLength; i++) {
            char temp = s[i];
            s[i] = s[n - i];
            s[n - i] = temp;
        }
        return new String(s);
    }

    /**
     * 方法六：（利用位异或操作，交换两头数据）
     * @param s
     * @return
     */
    public static String reverse6(String s) {

        char[] str = s.toCharArray();

        int begin = 0;
        int end = s.length() - 1;
        while (begin < end) {
            str[begin] = (char) (str[begin] ^ str[end]);
            str[end] = (char) (str[begin] ^ str[end]);
            str[begin] = (char) (str[end] ^ str[begin]);
            begin++;
            end--;
        }
        return new String(str);
    }

    /**
     * 方法七：（利用栈结构）
     * @param s
     * @return
     */
    public static String reverse7(String s) {
        char[] str = s.toCharArray();
        Stack<Character> stack = new Stack<Character>();
        for (int i = 0; i < str.length; i++)
            stack.push(str[i]);

        String reversed = "";
        for (int i = 0; i < str.length; i++)
            reversed += stack.pop();

        return reversed;
    }

    public static void main(String[] args){
         StringReverse r = new StringReverse();
         String s1 = r.reverse1("gfedcba");
         String s2 = r.reverse2("gfedcba");
         String s3 = r.reverse3("gfedcba");
         String s4 = r.reverse4("gfedcba");
         String s5 = r.reverse5("gfedcba");
         String s6 = r.reverse6("gfedcba");
         String s7 = r.reverse7("gfedcba");
         System.out.println(s1);
         System.out.println(s2);
         System.out.println(s3);
         System.out.println(s4);
         System.out.println(s5);
         System.out.println(s6);
         System.out.println(s7);
	}
}
