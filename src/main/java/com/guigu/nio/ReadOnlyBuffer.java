package com.guigu.nio;

import java.nio.ByteBuffer;

/**
 * 只读Buffer,如果put会抛异常java.nio.ReadOnlyBufferException
 */
public class ReadOnlyBuffer {
    public static void main(String[] args) {
        //创建一个ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);
        for (int i = 0; i < 64; i++) {
            byteBuffer.put((byte) i);
        }

        byteBuffer.flip();//读取

        //得到一个只读的Buffer
        ByteBuffer readOnlyBuffer = byteBuffer.asReadOnlyBuffer();
        System.out.println(readOnlyBuffer.getClass());

        //读取
        while (readOnlyBuffer.hasRemaining()){
            System.out.println(readOnlyBuffer.get());
        }
//        readOnlyBuffer.put((byte) 100);
    }
}
