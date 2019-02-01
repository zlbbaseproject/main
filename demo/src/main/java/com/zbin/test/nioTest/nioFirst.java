package com.zbin.test.nioTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by lenovo on 2018/8/6.
 */
public class nioFirst {
    public static void main(String args[]){
        testBuffer();

    }

    public static void testBuffer(){
        ByteBuffer buf = ByteBuffer.allocate(48);
        outputBuffer(buf);
        buf.put((byte)2);
        buf.put((byte)3);
        System.out.println(buf.get());
        outputBuffer(buf);
        buf.flip();
        outputBuffer(buf);
        System.out.println(buf.get());
        System.out.println(buf.get());
        System.out.println(buf.get());
        outputBuffer(buf);
        buf.flip();
        outputBuffer(buf);
    }
    public static void outputBuffer(ByteBuffer buffer ){
        System.out.println("");
        System.out.print("capacity: " + buffer.capacity() + ", ");
        System.out.print("position: " + buffer.position() + ", ");
        System.out.println("limit: " + buffer.limit());

    }
    public static void readFile(){
        RandomAccessFile aFile = null;
        try {
            File f = new File("banner.txt");
            System.out.println(nioFirst.class.getResource("/banner.txt").getPath());

            aFile = new RandomAccessFile(f, "rw");
            FileChannel inChannel = aFile.getChannel();
            ByteBuffer buf = ByteBuffer.allocate(48);
            int bytesRead = inChannel.read(buf);

            while (bytesRead != -1) {

                buf.flip();
                while(buf.hasRemaining()){
                    System.out.print((char) buf.get());
                }
                buf.clear();
                bytesRead = inChannel.read(buf);
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(aFile != null) try {
                aFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
