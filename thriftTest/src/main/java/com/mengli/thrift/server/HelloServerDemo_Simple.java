package com.mengli.thrift.server;

import com.mengli.thrift.service.HelloWorldImpl;
import com.mengli.thrift.service.HelloWorldService;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;

/**
 * Created by Lily on 16/9/18.
 */
public class HelloServerDemo_Simple {
    public static final int SERVER_PORT = 8091;

    public void startServer() {
        try {
            System.out.println("HelloWorld TSimpleServer start .... port:" + SERVER_PORT);

//            TProcessor tprocessor = new HelloWorldService.Processor(new HelloWorldImpl());//HelloWorldService.Iface&gt;();
            TProcessor tprocessor = new HelloWorldService.Processor<HelloWorldService.Iface>(
                    new HelloWorldImpl());
            // HelloWorldService.Processor&lt;HelloWorldService.Iface&gt; tprocessor =
            // new HelloWorldService.Processor&lt;HelloWorldService.Iface&gt;(
            // new HelloWorldImpl());

            // 简单的单线程服务模型，一般用于测试
            TServerSocket serverTransport = new TServerSocket(SERVER_PORT);
            TServer.Args tArgs = new TServer.Args(serverTransport);
            tArgs.processor(tprocessor);
            tArgs.protocolFactory(new TBinaryProtocol.Factory());
            // tArgs.protocolFactory(new TCompactProtocol.Factory());
            // tArgs.protocolFactory(new TJSONProtocol.Factory());
            TServer server = new TSimpleServer(tArgs);
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
        HelloServerDemo_Simple server = new HelloServerDemo_Simple();
        server.startServer();
    }
}
