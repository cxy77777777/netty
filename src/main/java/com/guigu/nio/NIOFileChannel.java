package com.guigu.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * FileChannel实例1
 * 字符串写入到本地文件中
 */
public class NIOFileChannel {

    public static void main(String[] args)  throws Exception{
        String str = "hello,尚硅谷";
        //创建一个输出流Channel
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\bz\\sgg.txt");
        //通过fileOutputStream获取对应的FileChannel
        //这个FileChannel真实类型是FileChannelImpl
        FileChannel fileChannel = fileOutputStream.getChannel();

        //创建一个缓冲区ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //将str放入byteBuffer中
        byteBuffer.put(str.getBytes());

        //对byteBuffer进行flip，因为 str放入byteBuffer中后posrtion在最后面，需要flip转换postion才能从头开始.
        byteBuffer.flip();

        //将byteBuffer中的数据写入到fileChannel中
        fileChannel.write(byteBuffer);
        //关闭fileChannel
        fileChannel.close();
    }
}
