package com.example.demo.test;

import java.util.UUID;

/**
 * Created by TDW on 2019/2/13.
 */
public class test {
    public static void main(String[] args) {
        System.out.println(sub());
    }
    public static int sub(){
        int i = 1;
        try{

            return ++i;
        }catch(Exception e){

        }finally{ ++i;}
return i;
}
}