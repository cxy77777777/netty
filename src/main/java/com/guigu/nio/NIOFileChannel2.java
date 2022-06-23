package com.guigu.nio;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * FileChannel读取本地数据
 */
public class NIOFileChannel2 {
    public static void main(String[] args) throws Exception{
        //创建文件输入流
        File file = new File("D:\\bz\\sgg.txt");
        FileInputStream fileInputStream = new FileInputStream(file);

        //通过fileInputStream获取对应的FileChannel ->实际类型是 FileChannelImpl
        FileChannel channel = fileInputStream.getChannel();

        //创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate((int)file.length());

        //将通道的数据读入到Buffer
        channel.read(byteBuffer);

        //将byteBuffer的字节数据转成String
        System.out.println(new String(byteBuffer.array()));
        fileInputStream.close();

    }
}
