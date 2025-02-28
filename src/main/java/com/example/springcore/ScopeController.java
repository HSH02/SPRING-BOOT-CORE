package com.example.springcore;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScopeController {

    @Autowired
    private ScopedBean singletonBean;

    @Autowired
    private ScopedBean prototypeBean;

    @Autowired
    private ScopedBean requestBean;

    @Autowired
    private ScopedBean sessionBean;

    @Autowired
    private ScopedBean applicationBean;

    @Autowired
    private ScopedBean websocketBean;

    @Autowired
    private ApplicationContext context;

    @GetMapping("/scope-demo")
    public String getScopeBeansHtml(HttpServletRequest request) {
        StringBuilder html = new StringBuilder();
        // prototypeBean을 매번 새롭게 가져오기 위해서
        ScopedBean newPrototypeBean = context.getBean("prototypeBean", ScopedBean.class);

        html.append("<html><head><meta charset='UTF-8'><title>Bean Scope Demo</title></head><body>");
        html.append("<h1>Bean Scope Demo</h1>");
        html.append("<p>현재 세션 ID: ").append(request.getSession().getId()).append("</p>");
        html.append("<table border='1' cellpadding='8'>");
        html.append("<tr><th>Bean 스코프</th><th>인스턴스 (이름@해시코드)</th></tr>");
        html.append("<tr><td>singletonBean</td><td>").append(singletonBean.toString()).append("</td></tr>");
        html.append("<tr><td>prototypeBean (동적 호출)</td><td>")
                .append(newPrototypeBean.toString())
                .append("</td></tr>");
        html.append("<tr><td>requestBean</td><td>").append(requestBean.toString()).append("</td></tr>");
        html.append("<tr><td>sessionBean</td><td>").append(sessionBean.toString()).append("</td></tr>");
        html.append("<tr><td>applicationBean</td><td>").append(applicationBean.toString()).append("</td></tr>");
        html.append("<tr><td>websocketBean</td><td>").append(websocketBean.toString()).append("</td></tr>");
        html.append("</table>");
        html.append("<br/><a href='/scope-demo'>새로고침(현재 요청 재생성)</a>");
        html.append("</body></html>");
        return html.toString();
    }
}
