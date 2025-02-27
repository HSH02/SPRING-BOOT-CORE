package com.example.springcore.mvc.example1;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExecutionChain;

public class LoggingDispatcherServlet extends DispatcherServlet {
    public LoggingDispatcherServlet(AnnotationConfigWebApplicationContext context) {
        super(context);
    }

    @Override
    protected HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
        System.out.println("[DispatcherServlet] HandlerMapping 실행");
        HandlerExecutionChain chain = super.getHandler(request);
        System.out.println("[DispatcherServlet] HandlerMapping 완료");
        return chain;
    }

    @Override
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("[DispatcherServlet] doDispatch 시작");
        super.doDispatch(request, response);
        System.out.println("[DispatcherServlet] doDispatch 완료");
    }


}


