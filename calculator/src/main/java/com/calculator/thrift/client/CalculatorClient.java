package com.calculator.thrift.client;

import com.calculator.thrift.service.CalculatorService;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TNonblockingTransport;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by Lily on 16/9/19.
 */
public class CalculatorClient {
    public static final String SERVER_IP = "localhost";
    public static final int SERVER_PORT = 8098;
    public static final int TIMEOUT = 30000;

    /**
     * @param one
     * @param two
     */
    public void Client(double one, double two) {
        try {
            TAsyncClientManager clientManager = new TAsyncClientManager();
            TNonblockingTransport transport = new TNonblockingSocket(SERVER_IP,
                    SERVER_PORT, TIMEOUT);

            TProtocolFactory tprotocol = new TCompactProtocol.Factory();
            CalculatorService.AsyncClient asyncClient = new CalculatorService.AsyncClient(
                    tprotocol, clientManager, transport);

            CountDownLatch latch = new CountDownLatch(1);
            AsynCallback callBack = new AsynCallback(latch);
//            asyncClient.call(methodName, one, two, callBack);
            asyncClient.add(one, two, callBack);
            latch.await(30, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class AsynCallback implements AsyncMethodCallback<CalculatorService.AsyncClient.add_call> {
        private CountDownLatch latch;

        public AsynCallback(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void onComplete(CalculatorService.AsyncClient.add_call response) {
            try {
                System.out.println(response.getClass().getSimpleName() + " AsynCall result : "
                        + response.getResult());
            } catch (TException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        }

        @Override
        public void onError(Exception exception) {
            System.out.println("onError :" + exception.getMessage());
            latch.countDown();
        }
    }

    public static void main(String args[]) {
        CalculatorClient calculatorClient = new CalculatorClient();
        calculatorClient.Client(4, 2);
    }
}
