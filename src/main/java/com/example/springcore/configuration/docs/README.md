`# Spring Configuration 패키지의 이해

## 흐름도 다이어그램
```text
[MainRunner]
    ↓ 실행 
[AnnotationConfigApplicationContext(AppConfig)]
    ↓ 구성 클래스 로드 
[AppConfig (@Configuration)]
    ├── [ServiceConfig, RepositoryConfig, ProfileConfig]를 Import
    ↓ Bean 생성 및 의존성 주입
[ServiceConfig (@Configuration)]
    ├── Bean: MyService
    │      ↳ [RepositoryConfig.myRepository()]에 의존
    ↓
[RepositoryConfig (@Configuration)]
    ├── Bean: MyRepository
    │      ↳ MyService에 데이터를 제공
    ↓
[ProfileConfig (@Configuration)]
    ├── Bean: MyService (devService/prodService)
    │      ↳ 프로파일 기반으로 [RepositoryConfig.myRepository()]에 의존
    ↓
[MyRepositoryImpl, MyServiceImpl]
    ├── MyServiceImpl은 MyRepositoryImpl을 사용하여 작업을 수행
    └── MyServiceImpl은 환경별 메시지 (Environment-specific Message)도 사용
```


# Spring Configuration의 역할

애플리케이션이 필요로 하는 객체(Bean)와 이들 간의 관계를 설정하는 작업이다.

방법:
  1. Java 기반 (@Configuration과 @Bean).
  2. `<beans>` 태그로 설정(요즘은 잘 안 씀).
  3. 어노테이션 기반 (@Component, @Autowired).

# Component ↔ Bean ↔ Configuration의 관계

1. `@Component`
   - 역할 : 클래스 전체를 Spring IoC 컨테이너에서 관리되는 Bean으로 등록한다.
   - 자동 등록 방식 : `@ComponentScan을 통해 통해 패키지 내 클래스를 자동으로 탐지 및 등록한다
   - 주로 사용되는 예시 : `@Controller`, `@Serrivce`, `@Repository` ➡️ 역할별로 확장된 `@Component`의 확장 버전
   - 특징: 
     - 자동으로 등록
     - 메서드는 일반 메서드로 취급
     - 싱글톤 보장 없음 (내부 메서드 호출 시 새로운 객체 생성 가능)
2. `@Bean`  
   - 역할 : 특정 객체를 생성하여 Spring 컨테이너에 **수동으로 등록한다.**
   - 수동 등록 방식 : `@Configuration` 클래스 안에서 정의한다.
   - 주로 사용되는 예시 : 복잡한 의존성 설정, 외부 라이브러리 객체를 Spring Bean으로 등록
   - 결론 : 수동으로 Bean 등록.
3. `@Configuration`
   - 역할 : `@Bean` 메서드를 포함한 **Bean 정의의 모음**이다.. 
   - 주로 사용되는 예시 : 설정을 역할별로 분리한다.
   - 결혼 : 설정 관리 클래스.

## Configuration VS Component?

### 주요 차이점

1. 자동 등록 vs 수동 등록
- `@Component`: 클래스 전체를 자동으로 Bean으로 등록.
- `@Configuration`: 명시적으로 @Bean 메서드를 작성해 등록.
2. 싱글톤 보장
- `@Component`: 내부 메서드 호출 시 싱글톤 보장 없음.
- `@Configuration`: CGLIB 프록시를 통해 싱글톤 보장
```java
boolean proxyBeanMethods() default true;
```

### 예시: Spring Security 

- `@Configuration`:
  - 패스워드 암호화 같은 설정을 싱글톤으로 관리하여 동일한 암호화 로직 보장함
- `@Component`:
  - 내부 메서드 호출에서 새로운 객체가 생성될 수 있어, 암호화 로직이 달라질 가능성 있음.

## CGLIB(Code Generator Library) 란?

**CGLIB**는 **코드 생성 라이브러리**로, 런타임에 **동적으로 자바 클래스의 프록시를 생성**해주는 기능을 제공한다.

- **오픈 소스 라이브러리**로, Java 표준 라이브러리가 아니라 Spring에서 가져다 사용하는 도구.
- **Spring에서의 주요 역할**:
   1. **`@Configuration`의 싱글톤 보장**:
      - CGLIB을 통해 `@Bean` 메서드 호출을 가로채 동일한 객체를 반환.
   2. **AOP(Aspect-Oriented Programming)**:
      - 메서드 호출을 가로채 트랜잭션, 로깅 등 부가 로직을 추가.

## @Configuration(proxyEnabled = false)란?
proxyEnabled = false로 설정하면 `@Configuration`은 CGLIB 프록시를 비활성화한다.


결과적으로 `@Component`와 동일하게 동작하며, 싱글톤 보장이 사라짐.
   
## 왜 @Configuration은 @Component로 대체할 수 없는가?

- `@Configuration`은 복잡한 Bean 의존성 설정을 위해 설계.
- CGLIB 프록시를 통해 내부 메서드 호출 간에도 싱글톤을 보장.
- 반면, `@Component`는 프록시가 없어 메서드 호출마다 새로운 객체 생성 가능.
```java
@Configuration
public  class Config {

    @Bean
    public SimpleBean simpleBean() {
        return new SimpleBean();
    }

    @Bean
    public SimpleBeanConsumer simpleBeanConsumer() {
        return new SimpleBeanConsumer(simpleBean()); // CGLIB 프록시가 호출을 가로채어 이미 등록된 빈을 반환한다. ➡️ 싱글톤 보장
    }
}
```

```java
@Component
public  class Config {

    @Bean
    public SimpleBean simpleBean() {
        return new SimpleBean();
    }

    @Bean
    public SimpleBeanConsumer simpleBeanConsumer() {
        // 컴파일 오류 발생! : @Bean으로 어노테이션이 추가된 메서드는 직접 호출됩니다.
        return new SimpleBeanConsumer(simpleBean()); // 이 코드는 순수한 자바 메서드를 호출하는 것으로 작동하여 새 인스턴스를 반환한다. ➡️ 싱글톤 보장 실패
    }
}
```

이 코드는 같아 보이는 코드 지만

- **`@Configuration`의 경우**:
   - `b()` 메서드에서 `a()` 호출 시, CGLIB 프록시가 `a()` 호출을 가로채서 스프링 컨테이너에서 이미 등록된 빈을 반환한다.
   - 결과적으로 싱글톤을 보장한다.
- **`@Component`의 경우**:
   - `b()` 메서드에서 `a()`를 호출하면, 단순한 Java 메서드 호출로 처리됩니다.
   - 결과적으로 싱글톤을 보장하지 못한다.

## 정리 
- `@Component`
  - 자동 등록.
  - 메서드 호출 시 새로운 객체 생성 가능.
  - 간단한 Bean 등록에 적합.
- `@Configuration`
  - 설정 관리 클래스.
  - `@Bean` 메서드를 통해 Bean 생성 및 의존성 주입 관리.
  - 싱글톤 보장: 복잡한 설정에 적합.
- `@Bean`
  - 개별 Bean 정의.
  - 외부 라이브러리나 복잡한 객체 생성에 사용.


`