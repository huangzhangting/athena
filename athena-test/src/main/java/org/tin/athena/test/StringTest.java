package org.tin.athena.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringTest {

    public static void main(String[] args) {
        String s1 = new StringBuffer("计算机").append("转件").toString();
        System.out.println(s1.intern() == s1);

        //1.7版本以后，false，因为java特殊字符串，常量池中已经有了
        //不符合首次出现的原则
        String s2 = new StringBuffer("ja").append("va").toString();
        System.out.println(s2.intern() == s2);

        int head = 0;
        int len = 9;
        System.out.println( (head-1) & 8 );

        int i = 5;
        System.out.println(1 << i);

        String ss = ",,2019-12-27 17:03:04,2019-12-27 23:59:57,input_residence_time,\"{\"\"widget_name\"\":\"\"user_email\"\",\"\"input_residence_time\"\":1047}\",,USD,,SDK,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,AS,ID,JT,Debong Tengah,52132,None,114.5.102.42,false,Indosat Ooredoo,Indosat Ooredoo,Indonesia,1577466180600-1112224949849309483,36debf80-28e4-475f-bac0-c4f7c827f028,,,191908418758438912,,,android,OPPO-CPH1909,8.1.0,1.8.0,v4.10.0,com.pinjam.disini,Pinjam Disini,com.pinjam.disini,false,,,,true,Dalvik/2.1.0 (Linux; U; Android 8.1.0; CPH1909 Build/O11019),utm_source=google-play&utm_medium=organic,\n";
//        ss = "191908418758438912";
        Pattern pattern = Pattern.compile(",\\d{18},");
        Matcher matcher = pattern.matcher(ss);
        if(matcher.find()){
            String s = matcher.group();
            System.out.println(s);
        }

        Pattern pattern2 = Pattern.compile(",[0-9a-z]+-[0-9a-z]+-[0-9a-z]+-[0-9a-z]+-[0-9a-z]+,");
//        pattern2 = Pattern.compile(",[0-9a-z\\-]{36},");
        pattern2 = Pattern.compile(",[\\w\\-]{36},");
        Matcher matcher2 = pattern2.matcher(ss);
        if(matcher2.find()){
            String s = matcher2.group();
            System.out.println(s);
        }

    }

}
