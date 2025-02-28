package com.example.springcore;

public class ScopedBean {
    private String name;

    public ScopedBean(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        // 이름과 해시코드를 출력하여 인스턴스 식별에 도움을 줍니다.
        return name + "@" + Integer.toHexString(hashCode());
    }
}