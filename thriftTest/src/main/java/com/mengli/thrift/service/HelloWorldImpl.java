package com.mengli.thrift.service;

import org.apache.thrift.TException;

/**
 * Created by Lily on 16/9/18.
 */
public class HelloWorldImpl implements HelloWorldService.Iface {

    @Override
    public String sayHello(String username) throws TException {
        return "Hi," + username + " welcome to thrift";
    }
}
