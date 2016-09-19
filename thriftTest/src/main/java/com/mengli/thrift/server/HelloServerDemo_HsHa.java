package com.mengli.thrift.server;

import com.mengli.thrift.service.HelloWorldImpl;
import com.mengli.thrift.service.HelloWorldService;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;

/**
 * Created by Lily on 16/9/19.
 */
public class HelloServerDemo_HsHa {
    public static final int SERVER_PORT = 8094;

    public void startServer() {
        try {
            System.out.println("HelloWorld THsHaServer start ....port:" + SERVER_PORT);

            TProcessor tprocessor = new HelloWorldService.Processor<HelloWorldService.Iface>(new HelloWorldImpl());

            TNonblockingServerSocket tnbSocketTransport = new TNonblockingServerSocket(
                    SERVER_PORT);
            THsHaServer.Args thhsArgs = new THsHaServer.Args(tnbSocketTransport);
            thhsArgs.processor(tprocessor);
            thhsArgs.transportFactory(new TFramedTransport.Factory());
            thhsArgs.protocolFactory(new TBinaryProtocol.Factory());

            //半同步半异步的服务模型
            TServer server = new THsHaServer(thhsArgs);
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
        HelloServerDemo_HsHa server = new HelloServerDemo_HsHa();
        server.startServer();
    }
}
