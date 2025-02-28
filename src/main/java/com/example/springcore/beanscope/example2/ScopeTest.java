package com.example.springcore.beanscope.example2;

import org.springframework.context.support.SimpleThreadScope;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class ScopeTest {
    public static void main(String[] args) {
        // 웹 애플리케이션 컨텍스트를 위한 Mock ServletContext 생성
        MockServletContext servletContext = new MockServletContext();

        // AnnotationConfigWebApplicationContext 생성
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setServletContext(servletContext);
        context.register(AppConfig.class);

        // custom scope("websocket")를 등록합니다.
        context.getBeanFactory().registerScope("websocket", new SimpleThreadScope());

        // refresh()를 호출하여 컨텍스트를 초기화합니다.
        context.refresh();


        // ----- Singleton & Prototype Scope 테스트 -----
        System.out.println("----- Singleton & Prototype Scope 테스트 -----");
        ScopedBean singleton1 = context.getBean("singletonBean", ScopedBean.class);
        ScopedBean singleton2 = context.getBean("singletonBean", ScopedBean.class);
        System.out.println("singletonBean 1: " + singleton1);
        System.out.println("singletonBean 2: " + singleton2);
        System.out.println("동일 인스턴스 여부: " + (singleton1 == singleton2));
        System.out.println();

        ScopedBean prototype1 = context.getBean("prototypeBean", ScopedBean.class);
        ScopedBean prototype2 = context.getBean("prototypeBean", ScopedBean.class);
        System.out.println("prototypeBean 1: " + prototype1);
        System.out.println("prototypeBean 2: " + prototype2);
        System.out.println("동일 인스턴스 여부: " + (prototype1 == prototype2));
        System.out.println();

        // ----- 웹 스코프 테스트: request, session, application, websocket -----
        System.out.println("----- 웹 스코프 테스트 (Request, Session, Application, Websocket) -----\n");


        System.out.println("----- 첫 번째 호출 웹 스코프 테스트 -----");
        // 첫 번째 HTTP 요청 시뮬레이션
        MockHttpServletRequest request1 = new MockHttpServletRequest(servletContext);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request1));

        // request scope, session scope, application scope, websocket scope의 Bean 가져오기
        ScopedBean requestBean1 = context.getBean("requestBean", ScopedBean.class);
        ScopedBean sessionBean1 = context.getBean("sessionBean", ScopedBean.class);
        ScopedBean applicationBean1 = context.getBean("applicationBean", ScopedBean.class);
        ScopedBean websocketBean1 = context.getBean("websocketBean", ScopedBean.class);

        System.out.println("[Request 1]");
        System.out.println("requestBean: " + requestBean1);
        System.out.println("sessionBean: " + sessionBean1);
        System.out.println("applicationBean: " + applicationBean1);
        System.out.println("websocketBean: " + websocketBean1);
        System.out.println();

        // 첫 번째 요청 종료 (RequestContextHolder 초기화)
        RequestContextHolder.resetRequestAttributes();

        System.out.println("----- 두 번째 호출 웹 스코프 테스트 -----");

        // 두 번째 HTTP 요청 시뮬레이션
        MockHttpServletRequest request2 = new MockHttpServletRequest(servletContext);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request2));

        ScopedBean requestBean2 = context.getBean("requestBean", ScopedBean.class);
        ScopedBean sessionBean2 = context.getBean("sessionBean", ScopedBean.class);
        ScopedBean websocketBean2 = context.getBean("websocketBean", ScopedBean.class);

        System.out.println("[Request 2]");
        System.out.println("requestBean: " + requestBean2);
        System.out.println("sessionBean: " + sessionBean2);
        System.out.println("websocketBean: " + websocketBean2);
        System.out.println();

        /*
         * 참고:
         * - request scope의 경우 요청마다 새로운 인스턴스가 생성됩니다.
         * - session scope의 경우, 동일한 세션(MockHttpServletRequest의 기본 session 사용)에서는 동일한 인스턴스가 반환됩니다.
         *   (따라서 위 예제에서는 request1과 request2의 sessionBean이 다르게 보일 수 있는데, 만약 동일한 HttpSession 객체를 사용하도록
         *    설정하면 두 요청 간에도 동일한 sessionBean 인스턴스를 확인할 수 있습니다.)
         * - application scope는 컨텍스트 당 하나이므로 동일합니다.
         * - websocket scope는 WebSocket 세션마다 인스턴스가 생성되나, 여기서는 시뮬레이션으로 두 요청 간에 분리된 객체를 반환할 수 있습니다.
         */

        // RequestContextHolder 초기화 후 컨텍스트 종료
        RequestContextHolder.resetRequestAttributes();
        context.close();
    }
}
