package com.guigu.nio;

import java.nio.ByteBuffer;

/**
 * ByteBuffer顺序放入，取出时也要顺序取出，否则会报BufferUnderflowException异常
 * Exception in thread "main" java.nio.BufferUnderflowException
 * 取出要用对应的类型取出
 */
public class NIOByteBufferPutGet {
    public static void main(String[] args) {
        //创建一个ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);
        //类型化方式放入数据
        byteBuffer.putInt(1);
        byteBuffer.putLong(2);
        byteBuffer.putChar('尚');
        byteBuffer.putShort((short)3);
        //取出前转换，posation置为0
        byteBuffer.flip();
        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getLong());
        System.out.println(byteBuffer.getChar());
        System.out.println(byteBuffer.getShort());
    }
}
