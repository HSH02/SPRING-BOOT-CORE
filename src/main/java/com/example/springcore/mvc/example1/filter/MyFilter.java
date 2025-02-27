package com.example.springcore.mvc.example1.filter;

import jakarta.servlet.*;

import java.io.IOException;

public class MyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("[MyFilter] init() 실행");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("[MyFilter 실행] 요청 전 처리");
        filterChain.doFilter(request, response);
        System.out.println("[MyFilter 실행] 응답 후 처리");
    }

    @Override
    public void destroy() {
        System.out.println("[MyFilter] destroy() 실행");
    }
}
