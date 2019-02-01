package com.zbin.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

public class PolymorphismTest {
    public void say(){
        System.out.println("hello");
    }

    public int say(int i){
        System.out.println("hello" + i);
        HashMap map;
        ArrayList al;
        BigDecimal b;
        return i;
    }

    public static void main(String args[]){
        new PolymorphismTest().say();
        new PolymorphismTest().say(10);
    }
}
