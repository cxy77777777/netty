package com.guigu.bio;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * BIO同步并阻塞（传统阻塞型），就是传统的java io编程，
 * 其相关的类和接口在java.io包下，服务器实现模式为一个链接一个线程，
 * 即客户端有连接请求时服务器端就需要启动一个线程进行处理，
 * 如果这个连接不做任何事情，会造成不必要的线程开销。
 */
public class BIOServer {

    public static void main(String[] args) throws Exception {
        //java获取cpu核数的方法：
        //io密集型：2n
        //cpu密集型：n+1
//        int cpuNum = Runtime.getRuntime().availableProcessors()+1;
        int cpuNum = 2*Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = new ThreadPoolExecutor(4, //核心线程池大小
                cpuNum, //最大并发数
                10, //超时时间
                TimeUnit.SECONDS, //时间单位
                new LinkedBlockingQueue<Runnable>(cpuNum),//线程等候队列
                Executors.defaultThreadFactory(), //线程创建工厂
                new ThreadPoolExecutor.DiscardOldestPolicy());//拒绝策略
        CountDownLatch countDownLatch = new CountDownLatch(cpuNum);

        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务器启动了");
        while (true){
            System.out.println("等待连接....");
            //监听，等待客户端连接
            final Socket socket = serverSocket.accept();
            System.out.println("连接到一个客户端");
            System.out.println("主线程name：" + Thread.currentThread().getName());
            executorService.execute(new Runnable() {
                public void run() {
                    //可以和客户端通讯
                    handler(socket);
                }
            });
        }
    }

    //编写一个handler方法，和和客户端通讯
    public static void handler(Socket socket){
        try {
            System.out.println("子线程name：" + Thread.currentThread().getName());
            byte[] bytes = new byte[1024];
            InputStream inputStream = socket.getInputStream();

            //循环读取客户端发送的数据
            while (true){
                System.out.println("read子线程name：" + Thread.currentThread().getName());
                System.out.println("read....");
                int read = inputStream.read(bytes);
                if (read!=-1){
                    System.out.println(new String(bytes,0,read));
                }else {
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("关闭和client的连接");
            try {
                socket.close();
            }catch (Exception e){

            }
        }
    }
}
