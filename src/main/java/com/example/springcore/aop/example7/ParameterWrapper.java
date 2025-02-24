package com.example.springcore.aop.example7;

@Trackable("ParameterWrapper")
public class ParameterWrapper {
    private String data;

    public ParameterWrapper(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return "ParameterWrapper: " + data;
    }
}
