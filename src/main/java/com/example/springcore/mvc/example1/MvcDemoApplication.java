package com.example.springcore.mvc.example1;

import com.example.springcore.mvc.example1.filter.MyFilter;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.io.File;

/**
 * 실행 안됨, 타 프로젝트 참고
 * @see <a href="https://github.com/HSH02/2025-02-27-mvc-demo">MVC 예제</a>
 */
public class MvcDemoApplication {

    public static void main(String[] args) throws LifecycleException {
        // 1. 임베디드 Tomcat 설정
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        String contextPath = "";
        File docBase = new File(".");
        var tomcatContext = tomcat.addContext("", docBase.getAbsolutePath());



        // 2. Spring WebApplicationContext 생성 시, ServletContext를 설정
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(MyConfig.class);
        context.setServletContext(tomcatContext.getServletContext()); // ServletContext 주입
        context.refresh();

        // 3. DispatcherServlet(로그 추가한 버전) 등록
        LoggingDispatcherServlet dispatcherServlet = new LoggingDispatcherServlet(context);
        String servletName = "myDispatcherServlet";
        Tomcat.addServlet(tomcatContext, servletName, dispatcherServlet);
        tomcatContext.addServletMappingDecoded("/", servletName);

        // 4. Filter 등록 (수동)
        var filterDef = new FilterDef();
        filterDef.setFilterName("myFilter");
        filterDef.setFilter(new MyFilter());
        tomcatContext.addFilterDef(filterDef);
        var filterMap = new FilterMap();
        filterMap.setFilterName("myFilter");
        filterMap.addURLPattern("/*");
        tomcatContext.addFilterMap(filterMap);

        // 5. 톰캣 시작
        tomcat.start();
        System.out.println("[MyManualMvcApp] 톰캣 서버 시작 http://localhost:8080");
        tomcat.getServer().await();
    }
}
