# MVC 패턴 개요

MVC (Model-View-Controller) 패턴은 애플리케이션의 각 관심사를 분리하여 개발, 유지보수 및 확장성을 개선하기 위한 대표적인 아키텍처 패턴입니다.

# 1. MVC 패턴 구성 요소

- **Model:**
    - 애플리케이션의 데이터와 비즈니스 로직을 담당합니다.
    - 주로 도메인/비즈니스 로직을 담으며, 데이터베이스 접근(리포지토리)은 별도의 레이어로 구성될 수 있습니다.

- **View:**
    - 사용자에게 데이터를 표시하는 역할을 합니다.
    - 예) JSP, Thymeleaf, FreeMarker 등 템플릿 엔진을 이용한 화면 렌더링

- **Controller:**
    - 클라이언트의 요청을 받아 적절한 비즈니스 로직을 호출하고, 그 결과를 모델에 담아 뷰에 전달합니다.


# 2. MVC 요청 처리 흐름

Spring MVC의 요청 처리 과정은 다음과 같이 진행됩니다:

1. Filter 처리 (서블릿 컨테이너 단계):
   - 웹 애플리케이션이 시작될 때 초기화된 Filter가 모든 요청에 대해 먼저 실행됩니다.
   - 공통 작업(인코딩, 보안, CORS 설정 등)을 수행한 후, 요청을 다음 단계로 전달합니다.

2. 클라이언트 요청 수신 (DispatcherServlet 진입):
   - Filter 처리가 완료된 HTTP 요청이 DispatcherServlet에 도달합니다.
   - DispatcherServlet은 Spring MVC의 중앙 진입점으로 모든 요청을 수신합니다.
   
3. 핸들러 매핑 (getHandler 호출):
   - DispatcherServlet은 등록된 HandlerMapping을 통해 요청 URL에 매핑된 컨트롤러(핸들러)와 해당 인터셉터 체인을 결정합니다.

4. Interceptor preHandle 실행:
   - 핸들러 호출 전에, 등록된 모든 인터셉터의 preHandle 메서드가 순차적으로 실행되어 인증, 권한 체크 등 사전 처리를 수행합니다.
    
5. 핸들러 호출 (HandlerAdapter 사용):
   - HandlerAdapter가 결정된 컨트롤러의 메서드를 호출하여 비즈니스 로직을 실행합니다.

6. 결과 처리 및 뷰 렌더링:
   - 컨트롤러는 처리 결과를 Model 객체(또는 데이터)를 반환합니다.
   - ReturnValueHandler가 이 반환값을 처리하여, ViewResolver를 통해 적절한 뷰를 결정하거나 JSON 등으로 변환해 응답을 준비합니다.
     - 예) @ResponseBody, Jackson 라이브러리를 통한 JSON 변환 과정
7. Interceptor postHandle 및 afterCompletion 실행:
   - 컨트롤러 실행 후, postHandle 메서드가 호출되어 추가 처리를 수행합니다.
   - 뷰 렌더링과 응답 전송이 완료되면, afterCompletion 메서드가 호출되어 최종 정리 작업을 수행합니다.

<img src="https://supabase.mermaidchart.com/storage/v1/object/public/chatgpt-diagrams/2025-02-27/50ba2488-e46c-472e-88e0-2047534ea8e4.png"/>)

# 3. Spring MVC의 주요 컴포넌트

## 3-1. DispatcherServlet

<img src="https://www.tutorialspoint.com/spring/images/spring_dispatcherservlet.png"/>

- **역할**
    - HTTP 요청 수신 후, 어떤 컨트롤러(Handler)가 처리해야 할지 결정
    - 처리 결과(모델, 뷰 이름 등)를 기반으로 **뷰 렌더링**까지 제어
    - **Interceptor**와 협력하여, 요청-응답 사이사이에 공통 로직이 실행될 수 있도록 조율

## 3-2. Interceptor

- 특징: 
  - Spring MVC Handler(컨트롤러) 호출 전후 및 뷰 렌더링 후에만 개입합니다.
  - 스프링이 관리하는 요청(핸들러 매핑이 가능한) 흐름에만 적용됩니다.
  - 공통 로직을 처리할 때 주로 사용합니다.

```java
public interface HandlerInterceptor {
        /**
         * 컨트롤러 호출 전에 실행
         */
        default boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            // 예: 인증, 권한 체크 등
            return true; // true를 반환해야 다음 단계로 진행
        }

        /**
         * 컨트롤러 호출 후, 뷰 렌더링 전에 실행
         */
        default void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
            // 예: 컨트롤러 결과에 대한 추가 가공 처리
        }

        /**
         * 뷰 렌더링 후에 실행
         */
        default void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
            // 예: 리소스 정리, 로깅 등
        }
}
```
- preHandle:
    - 컨트롤러(핸들러)가 호출되기 전에 실행됩니다.
    - 이 단계에서 요청을 가로채서 사전 처리를 수행하고, 조건에 따라 요청 진행 여부를 결정합니다.

- postHandle:
    - 컨트롤러 실행 후, 그러나 뷰가 렌더링되기 전에 실행됩니다.
    - 여기서는 모델에 추가 데이터를 삽입하거나, 응답을 수정할 수 있습니다.

- afterCompletion:
    - 뷰 렌더링이 완료된 후에 실행되며, 최종적으로 리소스 정리나 예외 처리 로직을 수행합니다.


## 3-3. Filter
    
- 특징: 
  - 서블릿 컨테이너 차원에서 동작합니다
  - **DispatcherServlet 이전에** 모든 요청/응답에 대해 전역적으로 개입합니다.
  - 플랫폼 공통 기능을 처리할 때 주로 사용합니다.

```java
public interface Filter {
    // 초기화: 서블릿 컨테이너가 Filter를 생성할 때 한 번 호출됨
    default void init(FilterConfig filterConfig) throws ServletException {
    }

    // 요청/응답 처리: 모든 HTTP 요청에 대해 실행됨
    void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException;

    // 종료 처리: 서블릿 컨테이너가 Filter를 소멸시킬 때 호출됨
    default void destroy() {
    }
}
```

- 초기화 (init):
    - Filter가 시작될 때 필요한 초기 설정 작업을 수행합니다.
    - Filter는 웹 애플리케이션이 배포되고 서블릿 컨테이너(예, Tomcat)가 시작될 때 초기화됩니다.

- 요청/응답 처리 (doFilter):
  각 HTTP 요청이 들어올 때마다 doFilter 메서드가 호출됩니다.
    - 내부적으로 FilterChain의 doFilter를 호출하면 다음 Filter 또는 최종적으로 DispatcherServlet으로 요청이 전달됩니다.
    - 요청 전/후에 로깅, 보안 체크, 데이터 압축 등 전역 작업을 수행할 수 있습니다.

- 종료 (destroy):
    - Filter가 소멸될 때 리소스 정리 작업 등을 수행합니다.
 


# 4. 결론 

MVC 체인은 인터셉터나 필터 같은 전용 메커니즘을 제공하여 요청 처리 전후에 공통 작업을 할 수 있게 합니다.

AOP는 주로 빈(Bean) 메서드 단위로 횡단 관심사를 처리하기에, Filter/Interceptor처럼 MVC 전체 흐름을 전역적으로 다루기에는 제약이 있습니다.

그대신 Filter, Interceptor 등은 MVC 전체 흐름에서 공통 작업을 할 수 있게 합니다.

중요한 것은 DispatcherServlet 가 MVC의 핵심이며, 요청시 알맞은 매핑을 연결하고, 컨트롤러의 로직이 완료되면 View Resolver로 View를 반환합니다.

그리고 부가적으로 Filter으로 전역적·기술적 작업을 수행하며,  컨트롤러별·비즈니스 관점 사전/사후 작업은 Interceptor을 통해 가능합니다.

한줄로 요약하자면 

> Spring MVC는 DispatcherServlet을 중심으로 요청을 받아, 적절한 핸들러를 찾고, Interceptor를 통해 공통 로직을 처리한 뒤, 컨트롤러가 반환하는 결과를 
> ViewResolver로 렌더링한다. 전역적이고 기술적인 처리는 Filter가 담당합니다.