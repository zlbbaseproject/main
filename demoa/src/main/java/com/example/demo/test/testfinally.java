package com.example.demo.test;

/**
 * Created by TDW on 2019/2/27.
 */
public class testfinally {
    public static void main(String[] args) {
        System.out.println(ceshi().getName());
    }

    public static student ceshi(){
        student stu = new student("zhangsan");
        try{
            return stu;
        }catch(Exception e){

        }finally{
            stu.setName("finallyzhangsan");
        }
        return stu;
    }
}
class student{
    public student(String name) {
        this.name = name;
    }

    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}