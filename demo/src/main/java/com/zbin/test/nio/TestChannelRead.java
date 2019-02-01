package com.zbin.test.nio;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by zhenglibin on 2018/8/8.
 */
public class TestChannelRead {
    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("D:\\文档教程\\FindBugs-IDEA-1.0.1\\LICENSE-bcel.txt");
        // 获取通道
        FileChannel fileChannel = fileInputStream.getChannel();

        // 创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int length = -1;

        while((length =fileChannel.read(buffer) ) != -1){
            // 重设buffer，将limit设置为position，position设置为0
            buffer.flip();
            byte[] bytes = buffer.array();
            System.out.write(bytes, 0, length);

        }
        fileChannel.close();
        fileInputStream.close();
    }

}
