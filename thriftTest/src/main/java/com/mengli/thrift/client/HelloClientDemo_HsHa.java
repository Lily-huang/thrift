package com.mengli.thrift.client;

import com.mengli.thrift.service.HelloWorldService;
import com.mengli.thrift.utils.TFramedTransport2;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 * Created by Lily on 16/9/19.
 */
public class HelloClientDemo_HsHa {
    public static final String SERVER_IP = "localhost";
    public static final int SERVER_PORT = 8094;
    public static final int TIMEOUT = 30000;

    /**
     *
     * @param userName
     */
    public void startClient(String userName) {
        TFramedTransport2 transport = null;
        try {
            transport=new TFramedTransport2(new TSocket(SERVER_IP, SERVER_PORT, TIMEOUT));
//            TSocket socket = (TSocket)transport.getTransport();
//            transport = new TSocket(SERVER_IP, SERVER_PORT, TIMEOUT);
            // 协议要和服务端一致
            TProtocol protocol = new TBinaryProtocol(transport);
            // TProtocol protocol = new TCompactProtocol(transport);
            // TProtocol protocol = new TJSONProtocol(transport);
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
        HelloClientDemo_HsHa client = new HelloClientDemo_HsHa();
        client.startClient("Lily ("+SERVER_PORT+":"+HelloClientDemo_HsHa.class.getSimpleName()+")");

    }
}
