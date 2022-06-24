package com.guigu.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * 多个Buffer进行读写操作方式：
 * Scattering：将数据写入到buffer时，可以采用buffer数组，依次写入[分散]
 * Gathering：从buffer读取数据时，可以采用buffer数组，依次读
 */
public class ScatteringAndGatheringTest {
    public static void main(String[] args) throws Exception{
        //使用ServerSocketChannel 和 SocketChannel网络
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);

        //绑定端口到socket，并启动
        serverSocketChannel.socket().bind(inetSocketAddress);

        //创建buffer数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        //等待客户端连接(telnet)
        SocketChannel socketChannel = serverSocketChannel.accept();

        int messageLength = 8;
        //循环读取
        while (true){
            int byteRead = 0;
            while (byteRead<messageLength){
                socketChannel.read(byteBuffers);
                byteRead += 1;//累计读取的字节数
                System.out.println("byteRead=" + byteRead);
                //使用流打印，看看当前的这个buffer的postion和limit
                Arrays.asList(byteBuffers).stream().map(buffer -> "postion=" + buffer.position() + ",limit = " + buffer.limit()).
                        forEach(System.out::println);

                //将所有的buffer进行flip
                Arrays.asList(byteBuffers).forEach(byteBuffer -> byteBuffer.flip());
                //将数据读出显示到客户端
                long byteWirte = 0;
                while (byteWirte<messageLength){
                    socketChannel.write(byteBuffers);
                    byteWirte += 1;
                }

                //将所有的buffer进行clear
                Arrays.asList(byteBuffers).forEach(byteBuffer -> byteBuffer.clear());
                System.out.println("byteRead=" + byteRead + "byteWirte=" + byteWirte + "messageLength=" + messageLength);
            }
        }
    }
}
