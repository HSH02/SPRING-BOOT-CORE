# Spring Bean Scope

Spring에서 Bean Scope란 스프링 컨테이너가 Bean의 **생성 주기와 라이프사이클**을 어떻게 관리할지에 대한 정책을 의미합니다. 

애플리케이션 특성에 맞추어 **Bean이 언제 생성되고 언제 폐기되는지**를 정할 수 있습니다.

### 1. Singleton
- 설명: 스프링 컨테이너당 단 하나의 인스턴스만 생성됩니다. (기본 값)
  - 라이프사이클:
    컨테이너 시작 시점에 Bean이 로딩(생성) → 애플리케이션 종료 시까지 유지
  - 특징:
    메모리 사용을 최소화하고, 모든 의존성 주입 및 요청에서 동일 인스턴스가 재사용됩니다.
    상태가 없는(Stateless) 서비스나 공유가 가능한(Immutable) 자원에 적합합니다.
  - Thread-safety 이슈에 주의: 여러 쓰레드가 동시에 접근할 수 있으므로, 공유 자원을 안전하게 관리해야 합니다.
  - 단일 패턴(Singleton Pattern)과의 차이: 실제 코드 레벨의 Singleton 구현이 아니라, “스프링 컨테이너 당 하나”를 의미합니다. 컨테이너가 여러 개라면 인스턴스도 여러 개 생성될 수 
    있습니다.
- 예시:
```java
@Service
  public class MySingletonService {
      // ...
  }
```


### 2. Prototype
- 설명: Bean을 요청할 때마다(주입 시점마다) 새로운 인스턴스를 생성하는 스코프입니다.
- 라이프사이클:
매번 DI(의존성 주입) 시점에 Bean이 생성 → 스프링 컨테이너가 생성 후 바로 관리 종료
즉, 관리 종료 후에는 Bean 소멸도 개발자가 직접 처리해야 할 수 있습니다(AOP를 적용할 때 주의).
- 특징:
  - 각 요청 시점마다 독립적인 객체가 필요할 때 적합합니다.
  - 상태를 저장하거나 다른 인스턴스와 격리되어야 하는 상황에 사용합니다.
- 예시: 
```java
@Scope("prototype")
@Component
public class MyPrototypeService {
    // ...
}
```
### 3. Request (웹 애플리케이션 환경)
- 설명: HTTP 요청 단위로 Bean의 라이프사이클을 관리합니다.
- 라이프사이클:
하나의 HTTP 요청이 시작될 때 생성 → 요청이 끝나면 소멸
- 특징:
  - 같은 요청 내에서는 동일 인스턴스를 사용하므로, 요청 레벨에서 발생하는 데이터를 저장하기에 적합합니다.
  - proxyMode = ScopedProxyMode.TARGET_CLASS를 자주 사용하여, Request 스코프 Bean을 Singleton 스코프 Bean에서 안전하게 참조할 수 있게 만듭니다.
  - 웹 환경이 아닌 곳(단위 테스트, 배치 등)에서 사용 시 NoSuchBeanDefinitionException이 발생할 수 있습니다.
- 예시:
```java
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
public class MyRequestBean {
    // ...
}
```


### ScopedProxyMode.TARGET_CLASS란?

스프링에서 @Scope("request")나 @Scope("session") 같은 웹 스코프 Bean을 싱글톤 스코프 Bean이 직접 참조하면 문제가 생길 수 있습니다. 

왜냐하면, 싱글톤 Bean은 애플리케이션이 구동되는 순간부터 만들어지지만, Request/Session Bean은 각 요청/세션 시점에 생성되기 때문입니다.

이를 해결하기 위해 스프링이 프록시(Proxy) Bean를 생성하여, 호출 시점마다 실제 Request/Session Bean을 연결해주는 방식을 사용합니다.


### 4.  Session (웹 애플리케이션 환경)
- 설명: HTTP 세션이 유지되는 동안 Bean의 인스턴스를 관리합니다.
- 라이프사이클:
  세션 생성 시 Bean이 생성 → 세션 만료 혹은 로그아웃 시 소멸
- 특징:
  - 사용자별 세션 상태를 저장하는 것이 목적이므로, 로그인 정보나 사용자별 설정을 관리하기에 적합합니다.
  - 세션 범위를 넘어서는 접근이나 세션 만료 시점에 따른 예외 상황을 주의해야 합니다.
  - Request와 마찬가지로 proxyMode 사용이 일반적입니다.
- 예시:
```java
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
public class MySessionBean {
    // ...
}
```


### 5. Application (웹 애플리케이션 환경)
- 설명: ServletContext 범위와 동일하게 애플리케이션 구동부터 종료까지 Bean이 살아있습니다.
- 라이프사이클:
  톰캣 등 서블릿 컨테이너가 구동될 때 Bean이 생성 → 애플리케이션이 내려갈 때 소멸
- 특징:
  - 웹 애플리케이션 전역에서 공유되므로 사실상 Singleton과 유사하게 동작하지만, ServletContext에 종속됩니다.
  - 전역적으로 공유되는 설정, 캐싱, 공용 리소스 등에 사용합니다.
- 예시:
```java
@Scope(value = "application", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
public class MyApplicationBean {
    // ...
}
```
### 6. Websocket (웹 애플리케이션 환경)
- 설명: WebSocket 연결(세션)마다 Bean의 인스턴스를 생성합니다.
- 라이프사이클:
  - WebSocket 연결 성립 시 생성 → 연결이 끊어질 때 소멸
- 예시:
  - 웹소켓 연결은 지속적인 통신 채널을 형성하므로, 각 연결마다 독립적인 상태가 필요합니다.
  - 실시간 채팅 등에 사용됩니다.
```java
@Bean
@Scope(value = "websocket", proxyMode = ScopedProxyMode.TARGET_CLASS)
public ScopedBean websocketBean() {
    return new ScopedBean("websocketBean");
}
```

# 7. 결론 

- Bean Scope 선택은 애플리케이션의 동작, 성능, 유지보수성에 직접적인 영향을 미칩니다.
- 상태를 어떻게 다루는지, 동시성 문제가 있는지, 웹 환경 범위와 맞물린 라이프사이클을 충분히 검토 후 스코프를 지정해야 합니다.
- 간단히 요약하자면,
  - Singleton: stateless하고 자원을 공유하는 빈에 적합 
  - Prototype: 요청마다 새로운 인스턴스가 필요할 때 
  - Request/Session/Application/WebSocket: 웹 환경에서의 라이프사이클에 맞춰 Bean을 관리할 때

