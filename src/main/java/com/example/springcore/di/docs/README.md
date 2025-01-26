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

**Bean**Factory는 스프링의 기본적인 Ioc 컨테이너로, 빈의 생성과 관리를 담당한다.

ApplicationContext은 BeanFactor를 확장한 인터페이스로, 추가적인 부가기능을 제공한다.

주요 차이점은 **로딩 방식**이다.

BeanFactory: Lazy Loading 방식을 사용하여, 실제로 필요한 빈이 요청되기 전까지 빈을 로딩하지 않는다.

**ApplicationContext**: Eager Loading 방식을 사용하여, 컨테이너가 초기화될 때 모든 빈을 미리 로딩한다.

일반적으로는 ApplicationContext을 사용한다. 

### 3.2 객체 찾기 방식
#### 3.2.1 byType
- 동작 방식 : 
  - Bean의 타입으로 객체를 검색.
  - 타입이 일치하는 빈이 하나인 경우, 해당 빈을 주입.
  - 타입이 일치하는 빈이 여러 개인 경우, 예외가 발생.


```java
@Component("myBean")
public class MyBean {
    public void doSomething() {
        System.out.println("Hello from MyBean!");
    }
}

@Component
public class AnotherBean {

    private final MyBean myBean;

    @Autowired
    public AnotherBean(MyBean myBean) { 
        this.myBean = myBean;
    }

    public void execute() {
        myBean.doSomething();
    }
}
```

타입 충돌 시 `@Primary` 혹은 `@Qualifier`로 해결 필요.
주로 쓰이는 방법임.

#### 3.2.2 byName
- 동작 방식:
  - Bean 이름으로 객체를 검색.
  - applicationContext.getBean("beanName") 형태로 호출.

```java
@Component("myBean")
public class MyBean implements bean {
    public void doSomething() {
        System.out.println("Hello from MyBean!");
    }
}

@Component("myBean2")
public class MyBean implements bean {
    public void doSomething() {
        System.out.println("Hello from MyBean!");
    }
}


@Component
public class AnotherBean {

    private final bean myBean;

    @Autowired
    public AnotherBean(@Qualifier("myBean") MyBean myBean) {
        this.myBean = myBean;
    }

    public void execute() {
        myBean.doSomething();
    }
}
```
### byType VS byName 

| 방식     | 장점       |단점| 사용 예시               |
|--------|----------| -- |---------------------|
| byType | 타입기반으로 검색 |타입 중복 시 예외 발생| 역할이 명확할 때           | 
| byName | 이름 충돌 방지 |이름 변경 시 유지보수 어려움| 이름으로 Bean을 구분해야 할 때 |

## 4. `@Primary`, `@Profile`, `@Qualifier`
### 4.1 `@Primary`

여러 개의 Bean 중에서 하나의 Bean을 선택한다면 예외가 발생하며 Spring은 선택을 내리지 못한다.
 
`@Primary`를 붙인다면 Bean이 자동으로 선택된다.

이 빈이 기본 값이라고 선언하는 것과 같다.

- 예시 코드
```java

@Repository
@Primary // 기본적으로 주입될 Bean
public class PrimaryUserRepository implements UserRepository {
    @Override
    public String getUserName() {
        return "Primary User";
    }
}

@Repository
public class DefaultUserRepository implements UserRepository {
    @Override
    public String getUserName() {
        return "Default User";
    }
}

// 실행 결과: Primary User 출력
```
- `@Primary`의 우선순위 규칙

1. `@Qualifier`로 지정된 Bean
2. `@Primary`로 지정된 Bean
3. 그 외 중복이면 에러 발생


### 4.2 @Profile

스프링 애플리케이션에서 개발(dev), 운영(prod), 테스트(test) 등의 환경별로 다른 Bean을 쓰고 싶을 때, @Profile을 사용한다.


- 환경별 설정 파일 (application.properties 또는 application.yml) 
    ```text
    # application.properties
    spring.profiles.active=dev
    ``` 
    ```text
    # application.yml
      spring:
        profiles:
          active: dev
    ```

- 예시 코드
```java
@Repository
@Profile("prod")
public class ProdUserRepository implements UserRepository {
    @Override
    public String getUserName() {
        return "Production User";
    }
}

@Repository
@Profile("dev")
public class DevUserRepository implements UserRepository {
    @Override
    public String getUserName() {
        return "Developer User";
    }
}

@Repository
@Profile({"dev", "test"})
public class DevTestUserRepository implements UserRepository {
    @Override
    public String getUserName() {
        return "Dev/Test User";
    }
}
// spring.profiles.active=dev인 경우에는 DevUserRepository와 DevTestUserRepository가 활성화.
```


- `@Profile` 우선순위

1. spring.profiles.active에 명시된 환경
2. 명시 없음 ➡️ default Profile 사용
3. 다중 환경일 경우 해당 프로파일에 선언된 Bean들만 활성화

### 4.3 @Qualifier
`@Autowired` 는 타입 기반으로 Bean을 등록하지만, 특정 이름으로 등록해야 할 때가 있다.

이떄 쓰이는 것이 `@Qualifier` 로 이름을 지정하여 사용된다.

- 특정 Bean을 명시적으로 지정하는 방법
```java
@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(@Qualifier("customUserRepository") UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String getWelcomeMessage() {
        return "Welcome, " + userRepository.getUserName();
    }

}
```
- `@Qualifier`의 사용 사례

  - Bean을 이름으로 구분해야 하는 경우
  - 특정 Bean을 명시해서 주입하고 싶을 때

### 요약 
- `@Primary`: 기본 Bean 지정
- `@Profile`: 환경별로 다른 Bean을 등록
- `@Qualifier`: 이름으로 Bean을 지정

## 5. 순환 참조 에러

순환 참조는 두 개 이상의 빈이 서로를 의존할 때 발생한다.
예로 A는 B를 참조하고 B는 A가 참조할 때 무한 루프에 빠질 수 있다.

### 5.1 @Lazy와 프록시를 통한 해결법
- @Lazy 어노테이션의 역할

`@Lazy` 어노테이션은 해당 빈의 초기화를 지연시켜, 실제로 필요할 때까지 빈의 생성을 미룹니다.

이를 통해 순환 참조 문제를 해결 할 수 있습니다.

- 프록시를 활용한 순환 참조 해결

`@Lazy`를 사용하면 스프링은 해당 빈의 프록시 객체를 생성하여 주입합니다. 

이 프록시 객체는 실제 빈의 초기화를 지연시키고, 실제 메서드 호출 시에만 초기화하여 순환 참조를 방지합니다.

예시

```java
    @Autowired
    public A(@Lazy B b) {
        this.b = b;
    }

    @Autowired
    public B(@Lazy A a) {
        this.a = a;
    }
```


### 5.2 설계 변경을 통한 해결법
- 의존성 구조 재설계

순환 참조는 근본적으로 설계상의 문제일 수 있으므로, 의존성 구조를 재검토하여 순환 의존을 제거하는 것이 좋습니다.

예시 코드는 다음과 같습니다.

```java
@Component
public class A {
    private final Mediator mediator;

    @Autowired
    public A(Mediator mediator) {
        this.mediator = mediator;
    }

    public void doSomething() {
        System.out.println("A is doing something");
        mediator.mediate("Action from A");
    }
}

@Component
public class B {
    private final Mediator mediator;

    @Autowired
    public B(Mediator mediator) {
        this.mediator = mediator;
    }

    public void doSomething() {
        System.out.println("B is doing something");
        mediator.mediate("Action from B");
    }
}

@Component
public class Mediator {
    public void mediate(String action) {
        System.out.println("Mediator is handling: " + action);
    }
}
```

## 6. Bean 이름 충돌 문제

스프링 컨테이너에서 동일한 타입의 빈이 여러 개 존재할 때, 어떤 빈을 주입해야 할지 모호해지는 문제가 발생할 수 있습니다.

이때는 `@Primary` 혹은 `@Qualifier`을 통해 해결할 수 있습니다.

### 6.1 @Primary를 통한 해결법

`@Primary` 어노테이션은 동일한 타입의 빈이 여러 개 있을 때, 우선적으로 주입할 빈을 지정합니다.

```java
@Repository
@Primary // 기본적으로 주입될 Bean
public class PrimaryUserRepository implements UserRepository {
    @Override
    public String getUserName() {
        return "Primary User";
    }
}

@Repository
public class DefaultUserRepository implements UserRepository {
    @Override
    public String getUserName() {
        return "Default User";
    }
}
```
위와 같이 `@Primary`를 사용하면, UserRepository 타입의 빈을 주입할 때 PrimaryUserRepository가 우선적으로 선택됩니다.

### 6.2 @Qualifier을 통한 해결법
- @Primary와 @Qualifier를 활용한 Bean 충돌 해결

`@Qualifier` 어노테이션은 빈 주입 시 특정 빈을 명시적으로 지정할 수 있도록 도와줍니다.

```java
@Repository("customUserRepository") // 특정 이름을 지정
public class CustomUserRepository implements UserRepository {

    @Override
    public String getUserName() {
        return "Custom User";
    }

}

@Repository("defaultUserRepository") // 특정 이름을 지정
public class DefaultUserRepository implements UserRepository {

    @Override
    public String getUserName() {
        return "Default User";
    }

}

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(@Qualifier("customUserRepository") UserRepository userRepository) {
        this.userRepository = userRepository;
    }

}
```

위와 같이 `@Qualifier`를 사용하여 특정 빈을 지정할 수 있습니다.


### 6.3 @Primary와 @Qualifier의 우선순위

- `@Qualifier`가 지정된 경우, 해당 빈이 우선적으로 주입됩니다.
- `@Qualifier`가 없을 때는 `@Primary`가 지정된 빈이 주입됩니다.



## 7. 결론
- 의존성 주입의 중요성 요약

의존성 주입은 스프링의 주요 개념으로 개발에만 구현에 집중할 수 있게 되었습니다.

SOLID의 DIP와 관계가 있으며, 만약 테스트나 도중에 변경해야 할 상황이 닥쳐도 금방 변경할 수 있게 만들어줍니다.

- Spring Boot에서의 DI 활용 방안

Spring Boot에서는 테스트를 위한 변경이나, 각 환경에 따라 달리 주입할 수 있습니다.


