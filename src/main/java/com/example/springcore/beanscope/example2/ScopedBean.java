package com.example.springcore.beanscope.example2;

public class ScopedBean {
    private String name;

    public ScopedBean(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + "@" + Integer.toHexString(hashCode());
    }
}
