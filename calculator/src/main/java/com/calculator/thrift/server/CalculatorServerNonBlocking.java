package com.calculator.thrift.server;

import com.calculator.thrift.service.CalculatorService;
import com.calculator.thrift.service.CalculatorServiceImpl;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;

/**
 * Created by Lily on 16/9/19.
 */
public class CalculatorServerNonBlocking {
    public static final int SERVER_PORT = 8098;

    public void startServer() {
        try {
            System.out.println("CalculatorServerNonBlocking start ....port:" + SERVER_PORT);
            TProcessor tprocessor = new CalculatorService.Processor<CalculatorService.Iface>(new CalculatorServiceImpl());

            TNonblockingServerSocket tnbSocketTransport = new TNonblockingServerSocket(SERVER_PORT);
            TNonblockingServer.Args tnbArgs = new TNonblockingServer.Args(tnbSocketTransport);
            tnbArgs.processor(tprocessor);
            tnbArgs.protocolFactory(new TCompactProtocol.Factory());
            tnbArgs.transportFactory(new TFramedTransport.Factory());

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
        CalculatorServerNonBlocking server = new CalculatorServerNonBlocking();
        server.startServer();
    }
}
