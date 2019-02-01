package com.zbin.test.cloneable;

/**
 * Created by zhenglibin on 2018/11/14.
 */
public class Student implements  Cloneable{
    private String name;

    private Department dept;

    public Student(String name, Department dept) {
        this.name = name;
        this.dept = dept;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Department getDept() {
        return dept;
    }

    public void setDept(Department dept) {
        this.dept = dept;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", dept=" + dept +
                '}';
    }
}
