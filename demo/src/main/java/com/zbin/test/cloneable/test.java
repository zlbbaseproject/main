package com.zbin.test.cloneable;

/**
 * Created by zhenglibin on 2018/11/14.
 */
public class test {
    public static void main(String args[]) throws CloneNotSupportedException {
        Department dept1 = new Department("计算机系");
        Department dept2 = new Department("经济管理系");
        String name = "张三";
        Student s = new Student(name,dept1);
        Student s2 = (Student) s.clone();

        System.out.println(s);
        System.out.println(s2);
        System.out.println(s2 == s);
        System.out.println(s2.getDept() == s.getDept());
        System.out.println(s2.getName() == s.getName());
        name = "lisi";
        s2.setDept(dept2);
        System.out.println(s);
        System.out.println(s2);
        System.out.println(s2 == s);
        System.out.println(s2.getDept() == s.getDept());
        System.out.println(s2.getName() == s.getName());

        String a = "张三";
        String b = new String("张三");
        String c = b;
        System.out.print(a==b);

    }
}
