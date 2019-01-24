package com.zbin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by TDW on 2019/1/21.
 */
@RestController
public class TestController {

    @RequestMapping(value = "/writefile")
    public String hiConfig(){
        fileWriteData(path);
        return "ok";
    }

    public static final String path = "E:\\test\\123.txt";
    public static void fileWriteData(String data){
        try{
            File file=new File(path);
            if(!file.exists()){
                file.createNewFile();
            }
            System.out.println(file.getAbsolutePath());
            long currentTime=System.currentTimeMillis();
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            Date date=new Date(currentTime);
            FileWriter fileWritter=new FileWriter(file,true);
            fileWritter.write(format.format(date)+"=====>"+data+"\n");
            fileWritter.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        fileWriteData(path);
    }
}
