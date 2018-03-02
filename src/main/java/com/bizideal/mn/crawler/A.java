package com.bizideal.mn.crawler;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : liulq
 * @date: 创建时间: 2017/10/30 14:51
 * @version: 1.0
 * @Description:
 */
public class A {

    public static void main(String[] args) {
        String s = "/playlist?id=649229404";
        Pattern pattern = Pattern.compile(".*\\?id=(\\d*)");
        Matcher matcher = pattern.matcher(s);
        if (matcher.matches())
            System.out.println(matcher.group(1));

        System.out.println(StringUtils.split("21万", "万")[0]);

        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        integers.add(3);
        integers.add(4);
        integers.forEach(i -> {
            if (i == 2)
                return;
            System.out.println(i);
        });
    }
}
