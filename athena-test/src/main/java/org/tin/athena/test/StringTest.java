package org.tin.athena.test;

public class StringTest {

    public static void main(String[] args) {
        String s1 = new StringBuffer("计算机").append("转件").toString();
        System.out.println(s1.intern() == s1);

        String s2 = new StringBuffer("ja").append("va").toString();
        System.out.println(s2.intern() == s2);
    }

}
