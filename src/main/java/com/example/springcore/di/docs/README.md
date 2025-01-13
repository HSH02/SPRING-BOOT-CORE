# 의존성 주입(Dependency Injection) 이란

## 1. 의존성 주입의 개념 및 장점
- 의존성 주입의 기본 개념

**D**ependency **I**njection, 일명 DI로 불리는 이 녀석은 Spring 내내 자꾸 이름을 비추면서 드러낸다.

DI란 무엇일까? 대체 왜 자꾸 등장하는 것일까?

간단히 말해, **의존성 주입은 객체의 생성과 의존 관계를 프레임워크가 대신 관리해주는 방식**이다.

과거 자바 개발에서는 객체를 직접 생성하고 관리해야 했지만, DI를 통해 이러한 과정을 Spring이 대신 처리해준다.

전통적인 자바의 예를 살펴보자. 다음과 같은 코드가 있다고 가정한다.

```java
Apple apple = new Apple();
```

Apple 객체를 만드는 간단한 코드다. 

그러나... 애플리케이션이 커지고 Apple 객체를 여러 군데에서 생성하게 된다면?

객체 생성 및 관리는 어떻게 해야 할까?

Spring은 **Bean**이라는 개념을 도입했다. 

Bean은 **Spring에 의해 관리되는 자바 객체**를 의미한다.

```java
@Component
class Apple {
    // ...
}

@Component
class FruitBasket {
    private final Apple apple;

    @Autowired
    public FruitBasket(Apple apple) {
        this.apple = apple;
    }
}
```

FruitBasket 클래스는 Apple 객체에 의존하고 있다. 

`@Autowired` 어노테이션을 통해 Spring은 Apple Bean을 자동으로 주입한다. 

이렇게 하면 객체 생성과 관리가 Spring에 의해 자동화되며 개발자는 비즈니스 로직에만 집중할 수 있다.

비유를 들자면, 전통적인 자바 개발은 냉장고에서 일일이 재료를 가져와 요리에 넣는 것과 같다. 

반면, DI를 사용하면 이미 준비된 재료를 손쉽게 요리에 넣는 것과 비슷하다.


- 의존성 주입을 사용하는 이유

의존성 주입을 사용하는 주 이유는 **객체 간의 결합도를 낮추고, 코드의 재사용성과 유지보수성을 높이기 위함**이다.

1. 결합도 감소
    - 클래스들이 다른 클래스에 의존하지 않고, 인터페이스나 추상화에 의존하게 되어 변경에 유연해진다. SOLID의 DIP(의존관계 역전 법칙) 을 떠올리면 된다.
2. 재사용성 향상
   - 의존성이 외부에서 주입되어, 같은 클래스가 여러 환경에서 재사용된다.
3. 유지보수성 증대
    - 의존성 관리가 중앙화 되어 있으므로, 객체 간의 관계를 쉽게 파악하고 변경할 수 있다.
4. 테스트 용이성
    - 의존성을 주입받고, 인터페이스에 의존하므로 만약 테스트를 할 시 모의 객체(Mock)을 주입하거나 직접 만든 테스트 객체를 주입하므로써 테스트를 수행할 수 있다. 

- 의존성 주입의 주요 장점 
  최종적으로 DI가 왜 쓰이는가? 라는 답변에는 다음과 같이 답변할 수 있다.

1. 테스트 용이성: 인터페이스에 의존하고, 주입받으므로 테스트 객체나 단위 테스트에 유리하다.
2. 결합도 감소: SOLID의 DIP 법칙에 따라 인터페이스에 의존하므로 변경에 유연해진다.
3. 재사용성 향상: DI를 통해 객체 생성/관리를 외부에서 처리하므로, 여러 곳에서 사용할 수 있다.
4. 유지보수성 증대: 객체를 중앙에서 관리하므로, 코드의 유지보수가 쉬워진다.
5. 확장성: DI를 통해 새로운 기능이나 객체 변경이 필요할 경우, 기존 코드를 크게 변경하지 않고도 확장이 가능하다.
6. 가독성: 중복 코드가 감소하고, 명확하게 각 클래스의 의존성을 드러낸다.

* 여기서 언급하는 중앙이란 Spring의 ApplicationContext을 의미한다.

## 2. Spring의 의존성 주입이란?

Spring 프레임워크는 의존성 주입(Dependency Injection, DI)을 핵심 기능으로 제공한다.

이 챕터에서는 Spring의 DI에 대해서 살펴본다.

### 2.1 들어가기 전: @Autowired에 대하여

**`@Autowired`** 어노테이션은 Spring에서 가장 많이 사용되는 의존성 주입 방법 중 하나다.

이 어노테이션은 Spring 컨테이너가 필요한 Bean을 자동으로 주입하도록 지시한다.

#### `@Autowired`의 주요 특징

- **자동 주입**: Spring이 타입을 기준으로 Bean을 찾아 주입한다.
- **선택적 주입**: `required` 속성을 통해 주입 여부를 선택할 수 있다.

### 2.2 Setter 주입

Setter 주입은 의존성을 주입하기 위해 세터 메소드를 사용하는 방식이다.


선택적으로 의존성 주입을 할 때 사용되며, 의존성을 변경해준다.

예제
```java
@Component
public class FruitBasket {

    private Apple apple;

    @Autowired
    public void setApple(Apple apple) {
        this.apple = apple;
    }
    
    // ...
}
```

| 장점      | 단점                | 
|---------|-------------------|
| 선택적 의존성 주입 가능  | 불변성을 보장하기 어려움|
| 순환 참조 해결에 유리   | 의존성 주입 시점이 명확하지 않음 | 

### 2.3 Field 주입

Field 주입은 의존성을 필드에 직접 주입하는 방식이다.

필드에 직정 `@Autowired`를 사용한다.

```java
@Component
public class FruitBasket {

    @Autowired
    private Apple apple;
    
}
```
| 장점        | 단점                | 
|-----------|-------------------|
| 간결하고 쉬움   | 테스트 어려움           |
| 빠른 의존성 관리 | 불변성을 보장하기 어려움     |
| -         | 의존성이 명확하게 드러나지 않음 |

### 2.4 생성자 주입

생성자 주입은 의존성을 생성자를 통해 주입하는 방식이다.

주로 권장되는 방식으로, 불변성을 보장하고 테스트하기 용이하다.


```java
@Component
public class FruitBasket {

    private final Apple apple;

    @Autowired
    public FruitBasket(Apple apple) {
        this.apple = apple;
    }
    
}
```
| 장점        | 단점                | 
|-----------|-------------------|
| 불변성 보장   | 테스트 어려움           |
| 의존성이 명확하게 드러남 | 불변성을 보장하기 어려움     |
| -         | 의존성이 명확하게 드러나지 않음 |

### 2.5 주입 방식 비교 

| 주입 방식    | 특징                           | 사용 예시                      |
|----------|------------------------------|----------------------------| 
| Setter 주입 | 선택적 의존성 주입 가능, 유연성, 순환 참조에 유리 | 선택적 의존성이나 의존성 변경 가능성이 있을 시 |
| 필드 주입    | 코드가 간결하고 구현 쉬움, 빠른 의존성 주입    | 간단한 테스트 경우에                |
| 생성자 주입   | 불변성 보장, 의존성이 명확함, 테스트 용이     | 필수의존성 주입시, 불변성이 중요할 때      |

## 3. Spring 컨테이너의 핵심 개념


### 3.1 ApplicationContext vs. BeanFactory
- ApplicationContext와 BeanFactory의 차이점
- 각 컨테이너의 특징 및 사용 사례

### 3.2 객체 찾기 방식
#### 3.2.1 byType
#### 3.2.2 byName
- byType과 byName의 차이점
- 각각의 장단점 및 사용 시기

## 4. Bean 관리 및 구성
### 4.1 Bean의 정의와 생명주기
- Bean의 개념
- Bean의 생성, 초기화, 소멸 과정

### 4.2 @Component와 그 파생 어노테이션
- @Component, @Service, @Repository, @Controller의 역할과 차이점
- 스프링 애노테이션을 활용한 Bean 정의

### 4.3 @ComponentScan
- @ComponentScan의 역할
- 패키지 스캔 설정 방법

### 4.4 @SpringBootApplication
- @SpringBootApplication의 구성 요소
- 자동 설정, 컴포넌트 스캔, Spring Boot 설정의 통합

## 5. Annotation 기반 의존성 주입 외의 방법
### 5.1 Java Configuration
- @Configuration과 @Bean 어노테이션
- Java 클래스를 통한 Bean 설정

### 5.2 XML 기반 설정
- XML 파일을 통한 Bean 정의
- XML 설정의 장단점

## 6. 추가적인 Bean 설정 어노테이션
### 6.1 @Value
- 프로퍼티 값을 주입하는 방법
- @Value의 다양한 사용 사례

### 6.2 @PropertySource
- 외부 프로퍼티 파일 로딩
- @PropertySource와 @Value의 연계 사용

### 6.3 @Conditional
- 조건부 Bean 등록
- @Conditional의 다양한 구현과 사용 예제

### 6.4 @Import
- 다른 설정 클래스 또는 Bean을 가져오는 방법
- @Import의 활용 사례

## 7. @Primary, @Profile, @Qualifier
### 7.1 @Primary
- 기본 Bean 설정 방법
- @Primary의 우선순위 규칙

### 7.2 @Profile
- 환경별 Bean 등록
- @Profile을 활용한 다중 환경 설정

### 7.3 @Qualifier
- 특정 Bean을 명시적으로 지정하는 방법
- @Qualifier의 사용 사례

## 8. 순환 참조 에러
### 8.1 @Lazy와 프록시를 통한 해결법
- @Lazy 어노테이션의 역할
- 프록시를 활용한 순환 참조 해결

### 8.2 설계 변경을 통한 해결법
- 의존성 구조 재설계
- 순환 참조를 피하기 위한 디자인 패턴

## 9. Bean 이름 충돌 문제
### 9.1 @Primary를 통한 해결법
### 9.2 @Qualifier을 통한 해결법
- @Primary와 @Qualifier를 활용한 Bean 충돌 해결

## 10. Best Practices (권장 사항)
- 의존성 주입 시 피해야 할 패턴
- 클린 코드와 의존성 주입의 연관성
- 유지보수성과 확장성을 고려한 설계 팁

## 11. 의존성 주입과 테스트
### 11.1 단위 테스트
- Mocking과 의존성 주입
- 단위 테스트에서의 DI 활용 방법

### 11.2 통합 테스트
- 통합 테스트 환경 설정
- 실제 Bean을 활용한 테스트 시나리오

## 12. 추가적인 유용한 어노테이션
### 12.1 @Resource
- @Resource의 역할과 사용법
- @Autowired와 @Resource의 비교

### 12.2 @Inject
- JSR-330의 @Inject 어노테이션
- Spring의 @Autowired와의 차이점

## 13. JSR-330 어노테이션
### 13.1 JSR-330 어노테이션이란?
- JSR-330의 정의와 목적
- 주요 어노테이션 소개 (@Inject, @Named, @Singleton 등)

### 13.2 Spring과 JSR-330 어노테이션의 관계
- Spring에서의 JSR-330 지원
- Spring 전용 어노테이션과 JSR-330의 비교

### 13.3 Spring Annotations ↔️ JSR-330 Annotations Table
| 기능            | Spring 전용 어노테이션    | JSR-330 어노테이션   |
|-----------------|--------------------------|---------------------|
| 의존성 주입     | `@Autowired`             | `@Inject`           |
| 빈 식별         | `@Qualifier`             | `@Named`            |
| 스코프 지정     | `@Scope`                 | `@Singleton`        |
| 기타 어노테이션 | `@Component`, `@Service` 등 | `@Named` 등         |

### 13.4 JSR-330 어노테이션 사용 예제
- `@Inject` vs `@Autowired`
- `@Named` vs `@Qualifier`
- 실제 코드 예제를 통한 비교

## 14. 결론
- 의존성 주입의 중요성 요약
- Spring에서의 DI 활용 방안
- 표준화된 어노테이션의 장점과 권장 사용 방안

