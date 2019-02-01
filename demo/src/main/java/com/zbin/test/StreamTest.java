package com.zbin.test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by zhenglibin on 2019/1/7.
 */
public class StreamTest {
    public static void main(String[] args) {
        test1();
    }
    public static void test1(){
        Stream<String> stream = Stream.of("a", "b", "c");
        stream.map(String::toUpperCase).collect(Collectors.toList()).forEach(System.out::println);
    }
}
