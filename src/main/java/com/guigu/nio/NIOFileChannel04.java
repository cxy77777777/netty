package com.guigu.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * FileChannel拷贝文件
 */
public class NIOFileChannel04 {
    public static void main(String[] args) throws Exception{
        //创建相关流
        FileInputStream fileInputStream = new FileInputStream("D:\\12.jpg");
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\13.jpg");

        //获取各个流对应的FileChannel
        FileChannel sourceCh = fileInputStream.getChannel();
        FileChannel destCh = fileOutputStream.getChannel();

        //使用transferFrom完成拷贝
        destCh.transferFrom(sourceCh,0, sourceCh.size());

        //关闭相关通道和流
    }
}
