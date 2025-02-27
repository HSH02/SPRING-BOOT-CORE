package com.example.springcore.mvc.example1.repository;

import com.example.springcore.mvc.example1.db.Database;
import org.springframework.stereotype.Repository;

@Repository
public class MyRepository {

    private final Database database;

    public MyRepository(Database database) {
        this.database = database;
    }

    public String getData() {
        System.out.println("[MyRepository] DB 접근 시작");
        String result = database.fetch();
        System.out.println("[MyRepository] DB 접근 완료");
        return result;
    }
}
