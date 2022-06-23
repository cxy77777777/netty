package com.guigu.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * FileChannel读取本地数据并写入到本地文件中
 */
public class NIOFileChannel03 {
    public static void main(String[] args) throws Exception{
        FileInputStream fileInputStream = new FileInputStream("1.txt");
        FileChannel channel = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("2.txt");
        FileChannel channel1 = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);

        while (true){   //循环读取
            //这里有一个重要操作，一定不能忘记
            //public final Buffer clear() {
            //        position = 0;
            //        limit = capacity;
            //        mark = -1;
            //        return this;
            //    }
            byteBuffer.clear(); //  清空buffer
            int read = channel.read(byteBuffer);
            if (read == -1){    //读取完毕
                break;
            }
            //将buffer中的数据写到channel1中 ---2.txt
            byteBuffer.flip();
            channel1.write(byteBuffer);
        }
        //关闭相关的流
        fileInputStream.close();
        fileOutputStream.close();
    }
}
