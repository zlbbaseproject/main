package com.zbin.model;

import java.io.*;
import java.util.HashMap;

public class User implements Serializable{
    private Integer userId;

    private String userName;

    private String desc;

    private String phone;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public static void main(String args[]) throws IOException {
        HashMap<String,String> hashMap = new HashMap<String,String>();
        hashMap.put("1","test");

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("obj.txt"))) {
            try {
                oos.writeObject(hashMap);
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            try (ObjectInputStream ooi = new ObjectInputStream(new FileInputStream("obj.txt"))) {
                HashMap hashMap1 = (HashMap) ooi.readObject();
                System.out.println(hashMap1);
                ooi.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //writeObj();
        //readObj();
    }

    public static void writeObj(Object obj)  {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("obj.txt"));
            User user = new User();
            user.setUserId(1);
            user.setUserName("test");
            oos.writeObject(user);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public User() {
    }

    public User(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", desc='" + desc + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    public static void readObj(){
        try {
            ObjectInputStream ooi=new ObjectInputStream(new FileInputStream("obj.txt"));
            try {
                User user = (User)ooi.readObject();
                System.out.println(user.getUserName());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}