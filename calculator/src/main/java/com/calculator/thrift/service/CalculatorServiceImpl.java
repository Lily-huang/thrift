package com.calculator.thrift.service;

import org.apache.thrift.TException;

/**
 * Created by Lily on 16/9/19.
 */
public class CalculatorServiceImpl implements CalculatorService.Iface {
    @Override
    public double add(double one, double two) throws TException {
        return one + two;
    }

    @Override
    public double sub(double one, double two) throws TException {
        return one - two;
    }

    @Override
    public double multi(double one, double two) throws TException {
        return one * two;
    }

    @Override
    public double div(double one, double two) throws TException {
        return one / two;
    }

    @Override
    public double call(String methodName, double one, double two) throws TException {
        if ("add".equals(methodName))
            return add(one, two);
        if ("sub".equals(methodName))
            return sub(one, two);
        if ("multi".equals(methodName))
            return multi(one, two);
        if ("div".equals(methodName))
            return div(one, two);
        return 0;
    }
}
