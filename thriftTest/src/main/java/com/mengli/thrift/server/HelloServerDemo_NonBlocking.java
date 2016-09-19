package com.mengli.thrift.server;

import com.mengli.thrift.service.HelloWorldImpl;
import com.mengli.thrift.service.HelloWorldService;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;

/**
 * Created by Lily on 16/9/19.
 */
public class HelloServerDemo_NonBlocking {
    public static final int SERVER_PORT = 8093;

    public void startServer() {
        try {
            System.out.println("HelloWorld TNonblockingServer start ....port:" + SERVER_PORT);

            TProcessor tprocessor = new HelloWorldService.Processor<HelloWorldService.Iface>(new HelloWorldImpl());

            TNonblockingServerSocket tnbSocketTransport = new TNonblockingServerSocket(
                    SERVER_PORT);
            TNonblockingServer.Args tnbArgs = new TNonblockingServer.Args(
                    tnbSocketTransport);
            tnbArgs.processor(tprocessor);
            tnbArgs.transportFactory(new TFramedTransport.Factory());
            tnbArgs.protocolFactory(new TCompactProtocol.Factory());

            // 使用非阻塞式IO，服务端和客户端需要指定TFramedTransport数据传输的方式
            TServer server = new TNonblockingServer(tnbArgs);
            server.serve();

        } catch (Exception e) {
            System.out.println("Server start error!!!");
            e.printStackTrace();
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        HelloServerDemo_NonBlocking server = new HelloServerDemo_NonBlocking();
        server.startServer();
    }
}
