# Spring 어노테이션 가이드

이 문서는 스프링 프레임워크에서 자주 사용되는 어노테이션들의 상세 속성 및 사용 예시를 정리한 자료입니다.

## 목차
- [@Service](#service)
- [@Repository](#repository)
- [@Component](#component)
- [@Autowired](#autowired)
- [@Controller](#controller)
- [@RequestMapping](#requestmapping)
- [@PathVariable](#pathvariable)
- [@RequestParam](#requestparam)
- [@SpringBootApplication](#springbootapplication)
- [@RestController](#restcontroller)
- [@Value](#value)
- [@Enable 계열 어노테이션](#enable-계열-어노테이션)
- [@Configuration](#configuration)
- [@Bean](#bean)

---

## @Service
**용도:**
- 서비스 계층의 비즈니스 로직을 담는 클래스에 사용
- 컴포넌트 스캔 시 자동으로 빈으로 등록

**주요 속성:**
- `value`: 빈 이름 지정 (선택적)

**예시:**
```java
@Service("orderService")
public class OrderService {
    public void processOrder() {
        // 비즈니스 로직
    }
}
```

---

## @Repository
**용도:**
- 데이터 접근 계층(DAO)에 사용
- 데이터베이스 예외를 스프링의 DataAccessException으로 변환

**주요 속성:**
- `value`: 빈 이름 지정 (선택적)

**예시:**
```java
@Repository("userRepository")
public class UserRepository {
    public User findById(Long id) {
        // 데이터베이스 접근 로직
        return new User();
    }
}
```

---

## @Component
**용도:**
- 특정 계층에 한정되지 않는 범용 컴포넌트에 사용
- 컴포넌트 스캔 시 자동 등록

**주요 속성:**
- `value`: 빈 이름 지정 (선택적)

**예시:**
```java
@Component("utilityBean")
public class Utility {
    public void doSomething() {
        // 공통 기능
    }
}
```

---

## @Autowired
**용도:**
- 스프링 컨테이너가 관리하는 빈을 자동 주입

**주요 속성:**
- `required` (기본값 true): 주입 대상이 필수인지 여부

**예시 (생성자 주입):**
```java
@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
```

---

## @Controller
**용도:**
- 웹 계층의 컨트롤러 클래스를 지정
- 요청을 처리하고 뷰 이름을 반환

**주요 속성:**
- `value`: 빈 이름 또는 URL 경로 별칭 지정 (선택적)

**예시:**
```java
@Controller
@RequestMapping("/user")
public class UserController {
    @RequestMapping("/profile")
    public String profile() {
        // 사용자 프로필 로직
        return "userProfile";
    }
}
```

---

## @RequestMapping
**용도:**
- URL 경로, HTTP 메서드, 파라미터, 헤더 등 요청 조건을 매핑

**주요 속성:**
- `value` 또는 `path`: URL 패턴 지정
- `method`: HTTP 메서드 지정 (GET, POST 등)
- `params`: 특정 파라미터 조건 지정
- `headers`: 특정 헤더 조건 지정
- `consumes`, `produces`: 요청 및 응답 미디어 타입 지정

**예시:**
```java
@Controller
@RequestMapping("/api")
public class ApiController {
    @RequestMapping(value = "/data", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Data getData() {
        return new Data();
    }
}
```

---

## @PathVariable
**용도:**
- URL 경로에 포함된 변수 값을 메서드 파라미터로 바인딩

**주요 속성:**
- `value` 또는 `name`: URL 템플릿 변수 이름 지정
- `required`: 변수 필수 여부 (기본 true)

**예시:**
```java
@Controller
@RequestMapping("/user")
public class UserController {
    @RequestMapping("/{id}")
    public String getUser(@PathVariable("id") Long userId) {
        // userId로 사용자 조회
        return "userDetail";
    }
}
```

---

## @RequestParam
**용도:**
- HTTP 요청 파라미터를 메서드 파라미터로 바인딩

**주요 속성:**
- `value` 또는 `name`: 요청 파라미터 이름
- `required`: 필수 여부 (기본 true)
- `defaultValue`: 값이 없을 때 사용할 기본값

**예시:**
```java
@Controller
@RequestMapping("/search")
public class SearchController {
    @RequestMapping("/query")
    public String search(@RequestParam(name = "q", required = false, defaultValue = "") String query) {
        // query 값을 사용한 검색 로직
        return "searchResults";
    }
}
```

---

## @SpringBootApplication
**용도:**
- 스프링 부트 애플리케이션의 시작점에 사용
- 내부적으로 @Configuration, @EnableAutoConfiguration, @ComponentScan을 포함

**주요 속성:**
- `scanBasePackages`: 컴포넌트 스캔할 기본 패키지 설정
- `exclude`, `excludeName`: 자동 설정 제외 대상

**예시:**
```java
@SpringBootApplication(scanBasePackages = "com.example.myapp")
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}
```

---

## @RestController
**용도:**
- RESTful 웹 서비스를 위한 컨트롤러로 사용
- @Controller와 @ResponseBody 역할 통합

**예시:**
```java
@RestController
@RequestMapping("/api")
public class ApiRestController {
    @RequestMapping("/info")
    public Info getInfo() {
        return new Info();
    }
}
```

---

## @Value
**용도:**
- 프로퍼티 파일이나 SpEL(Spring Expression Language)을 이용하여 값 주입

**주요 속성:**
- `value`: 주입할 값 (예: ${app.name} 또는 리터럴 값)

**예시:**
```java
@Component
public class AppConfig {
    @Value("${app.name}")
    private String appName;

    @Value("#{2 * T(Math).PI}")
    private double circumferenceConstant;
}
```

---

## @Enable 계열 어노테이션
**용도:**
- 특정 기능의 자동 설정이나 기능 활성화를 위해 사용

**예시:**
- **@EnableAutoConfiguration:** 스프링 부트 자동 설정 활성화
- **@EnableScheduling:** 스케줄링 작업 활성화
  ```java
  @Configuration
  @EnableScheduling
  public class SchedulingConfig {
      // 스케줄링 작업 등록
  }
  ```
- **@EnableAsync:** 비동기 메서드 실행 활성화
  ```java
  @Configuration
  @EnableAsync
  public class AsyncConfig {
      // 비동기 실행 설정
  }
  ```

---

## @Configuration
**용도:**
- 자바 기반 스프링 설정 클래스를 나타내며, @Bean 메서드를 통해 빈 정의 제공

**주요 속성:**
- `proxyBeanMethods` (기본값 true): 메서드 호출 시 빈 인스턴스의 프록시 사용 여부 결정

**예시:**
```java
@Configuration
public class AppConfig {
    @Bean
    public MyService myService() {
        return new MyServiceImpl();
    }
}
```

---

## @Bean
**용도:**
- @Configuration 클래스 내에서 스프링 컨테이너가 관리할 빈을 생성하는 메서드에 사용

**주요 속성:**
- `name`: 빈 이름 지정 (기본값: 메서드 이름)
- `initMethod`: 빈 초기화 시 호출할 메서드
- `destroyMethod`: 빈 소멸 시 호출할 메서드

**예시:**
```java
@Configuration
public class BeanConfig {
    @Bean(name = "customBean", initMethod = "init", destroyMethod = "cleanup")
    public CustomBean customBean() {
        return new CustomBean();
    }
}
```
