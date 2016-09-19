package com.mengli.thrift.client;

import com.mengli.thrift.service.HelloWorldService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 * Created by Lily on 16/9/19.
 */
public class HelloClientDemo_NonBlocking {
    public static final String SERVER_IP = "localhost";
    public static final int SERVER_PORT = 8093;
    public static final int TIMEOUT = 30000;

    /**
     *
     * @param userName
     */
    public void startClient(String userName) {
        TTransport transport = null;
        try {
            transport = new TFramedTransport(new TSocket(SERVER_IP,
                    SERVER_PORT, TIMEOUT));
            // 协议要和服务端一致
            TProtocol protocol = new TCompactProtocol(transport);
            HelloWorldService.Client client = new HelloWorldService.Client(
                    protocol);
            transport.open();
            String result = client.sayHello(userName);
            System.out.println("Thrift client result =: " + result);
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        } finally {
            if (null != transport) {
                transport.close();
            }
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        HelloClientDemo_NonBlocking client = new HelloClientDemo_NonBlocking();
        client.startClient("Lily ("+SERVER_PORT+":"+HelloClientDemo_NonBlocking.class.getSimpleName()+")");

    }
}
