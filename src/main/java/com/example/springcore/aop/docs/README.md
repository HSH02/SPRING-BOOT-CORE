# 1. AOP 란? 

AOP(Aspect-Oriented Programming)은 애플리케이션의 공통 관심사를 모듈화하여 
핵심 비즈니스 로직과 분리하는 프로그래밍 기법이다.

* 장점: 코드 중복을 줄이고 유지보수성을 높일 수 있다.


# 2. AOP는 어떻게 작동할까

Spring AOP는 프록시 기반으로 동작합니다.

* 대상 Bean 생성된 후, 해당 Bean을 프록시 객체로 감싸고, 클라이언트는 프록시를 통해 호출한다.
* 프록시는 메서드 호출을 가로채서, 미리 정의된 Advice를 적용한 후 실제 대상 메서드를 호출한다.
* 프록시의 생성 방식은 JDK 동적 프록시와 CGLIB 방식이 있으며, 대상 Bean의 인터페이스 구현 여부에 따라 선택된다.

# 3. Pointcut

**Join Point**는 Advice가 적용될 수 있는 프로그램 실행 중의 특정 지점을 의미합니다. 

메서드 실행이 Join Point에 해당합니다.

**Pointcut**은 이러한 Join Point들 중에서 어떤 지점에 Advice를 적용할 것인지를 선택하는 표현식입니다. 

즉, Pointcut은 "어느 지점을 Advice로 관여할 것인가?"를 정의하는 규칙입니다.

아래는 Pointcut의 종류입니다.

* execution() : 메서드의 실행(반환 타입, 메서드 명, 패키지, 인자) 등을 기반으로 조건을 지정한다.
* within() : 특정 클래스나 패키지 내부의 모든 Join Point를 지정한다.
* this() : 프록시 객체의 타입을 기준으로 타겟팅 한다.
* target() : 런타임 대상 객체의 실제 타입에 어노테이션이 붙어 있는지를 기반으로 한다.
* args(): 메서드 인자의 타입을 기준으로 한다.
* @annotation() : 메서드 선언부에 붙은 어노테이션을 기준으로 한다.
* @within() : 클래스 선언부에 붙은 어노테이션을 기준으로, 해당 클래스 관련 Join Point를 선택한다.
* @target() : 런타임 대상 객체의 타입에 붙은 어노테이션을 기준으로 한다.
* @args() : 메서드 인자에 붙은 어노테이션 정보를 기준으로 한다.

## 3-1. Pointcut 예시

### 3-1-0 들어가기전에...

Pointcut을 이해하기 전에 먼저 와일드 카드와 연산자에 대해 알아보겠습니다.

1. 와일드 카드 
   * `.*`
     * 단일 패키지 내에 속한 모든 클래스를 매칭합니다.
     * 예를 들어, com.example.*는 com.example 패키지에 속한 모든 클래스를 대상으로 합니다.
     * 단, com.example.sub와 같이 하위 패키지에 있는 클래스는 매칭되지 않습니다.
   * `..*`
     * 지정한 패키지와 그 하위 패키지에 속한 모든 클래스를 매칭합니다.
     * 예를 들어, com.example..*는 com.example 패키지와 그 하위의 모든 패키지(예: com.example.sub, com.example.sub.sub 등)에 속한 클래스를 대상으로 합니다.


2. 연산자

Pointcut 표현식에서는 논리 연산자를 사용하여 여러 조건을 결합할 수 있습니다.

* `&&`(AND 연산자)
  * 모든 조건이 충족될 때 매칭됩니다.
  * 예제:
    ```java 
    @Pointcut("execution(* *(..)) && args(..)")
    public void anyMethodRegardlessOfArgs() {}
    ```

* `||` (OR 연산자)
  * 조건 중 하나라도 참이면 매칭됩니다.
  * 예제:
    ```java 
    @Pointcut("execution(* *(..)) || args(..)")
    public void anyMethodRegardlessOfArgsOr() {}
    ```
* `!` (NOT 연산자)
  * 특정 조건을 부정할 때 사용합니다.
  * 예제:
    ```java 
    @Pointcut("!execution(public * com.example.service.*.*(..))")
    public void notPublicMethods() {}
    ```
* 괄호 () 
  * 복합 조건의 우선순위를 제어하기 위해 사용합니다. 
  * 예제: 
    ```java
    @Pointcut("(execution(* com.example..*(..)) || execution(* com.other..*(..))) && !execution(* com.example.internal..*(..))")
    public void complexPointcut() {}
    ```

### 3-1-1 execution()

특정 메서드 실행을 타겟팅하는 표현식입니다.
문법은 `execution(접근제어자, 반환타입 패키지.클래스.메소드(매개변수))` 과 같습니다.
접근 제어자는 선택사항이며, 접근 제어자를 생략할 경우 모든 접근 제어자를 포함합니다.


* 예제들


```java
@Pointcut("execution(public * com.*.*(..))")
public void allMethods() {}
```
 
1. 접근 제어자 : public
2. 반환 타입: * (모든 반환 타입) 
3. 패키지 : com
4. 클래스 : * (해당 패키지 내의 모든 클래스)
5. 메서드 : * (모든 메서드)
6. 매개변수: (..) (모든 종류와 개수의 매개변수)


```java
@Pointcut("execution(* com..*())")
public void allMethods() {}
```

1. 접근 제어자 : 생략, 모든 제어자
2. 반환 타입: * (모든 반환 타입)
3. 패키지: com
4. 클래스 : .. (해당 패키지와 하위 패키지 모두)
5. 메서드 : * (모든 메서드)
6. 매개변수 : () (인자가 없는 메서드)


```java
@Pointcut("execution(private String com..get*(String, ..))")
public void allMethods() {}
```

1. 접근 제어자 : private 
2. 반환 타입 : String
3. 패키지: com 
4. 클래스 : .. (해당 패키지와 하위 패키지 모두)
5. 메서드 : get* (get으로 시작하는 모든 메서드)
6. 매개변수 : String, .. (첫 번째 인자는 String 타입이어야 하고 그 뒤에는 0개 이상의 어떤 인자라도 허용)


```java
@Pointcut("execution(int com.math*.*(int, *))")
public void allMethods() {}
```

1. 접근 제어자 : 생략, 모든 제어자
2. 반환 타입 : int
3. 패키지: com
4. 클래스 : math* (패키지 내에서 math로 시작하는 클래스만)
5. 메서드 : * (모든 메서드)
6. 매개변수 : int, * (정확히 2개의 인자, 순서대로 int, 타입 상관 없음)()

### 3-1-2. within()

특정 클래스(또는 패키지) 내부의 모든 Join Point을 타겟팅합니다.

문법은 `within(패키지 혹은 클래스 경로)`과 같습니다.

* 예제들 

```java
@Pointcut("within(com.example.service.*)")
public void inServicePackage() {}
```

com.example.service 패키지에 속한 모든 클래스의 Join Point를 대상으로 합니다.
(하위 패키지 미포함)

```java
@Pointcut("within(com.example.service..*)")
public void inServicePackageAndSubpackages() {}
```

하위 패키지를 포함한 com.example.service의 하위 클래스의 Join Point를 대상으로 합니다.

```java
@Pointcut("within(com.example.service.MyService)")
public void inMyServiceClass() {}
```

com.example.service.MyService 클래스 내부에 정의된 모든 Join Point를 대상으로 합니다.

### 3-1-3. this()

Advice가 실행되는 시점에 사용되는 **프록시 객체**의  타입을 기준으로 Join Point를 선택합니다.

즉, 해당 프록시 객체가 지정한 타입에 속하는지 여부를 검사하여 매칭합니다.

객체의 실제 타입이나 인터페이스 타입을 기반으로 Advice 적용 대상을 결정합니다.

* 예제들

```java
@Pointcut("this(com.example.service.MyServiceInterface)")
public void proxyImplementsMyServiceInterface() {}
```

Advice 실행 시점에 생성된 프록시 객체가 MyServiceInterface 타입일 경우에만 매칭 됩니다.

즉, MyServiceInterface 타입을 구현한 경우 매칭됩니다.

```java
@Pointcut("this(com.example.service.MyService)")
public void proxyIsMyService() {}
```

Advice 실행 시점에 생성된 프록시 객체가 MyService 타입일 경우에만 매칭 됩니다.

단, JDK 동적 프록시에는 매칭되지 않고 CGLIB 프록시일 경우 매칭되니 주의합시다.

주로 인테페이스를 구현할 경우 JDK 프록시가 사용되고, 반대의 경우 CGLIB 프록시가 사용됩니다.

### 3-1-4. target()

Advice가 적용되는 시점에 원래의 대상 객체의 런타임 타입을 기준으로 Join Point를 선택합니다

프록시 내부에 감싸진 실제 객체의 타입이 지정한 타입과 일치항 경우 Advice가 적용됩니다.

this() 가 유사해보이지만, **this()는 프록시 객체 타입을 매칭**합니다.
그와 반대로 **target()은 실제 객체 타입을 매칭**합니다.

```java
@Pointcut("target(com.example.service.MyServiceImpl)")
public void targetMyService() {}

@Pointcut("this(com.example.service.MyServiceImpl)")
public void thisMyService() {}
```

Join Point를 선택하는 것이 같아보이지만 실행 시에는 다른 동작을 보입니다.

`MyServiceImpl`이 `MyServiceInterface `을 상속받아 JDK 동적 프록시가 사용된다 가정합시다.

이때 `MyServiceImpl`의 프록시 객체는 `MyServiceInterface`로 생성됩니다.

`target()`의 경우, **실제 객체를 기준으로 평가하므로 정상적으로 매칭됩니다.**

그러나 `this()`는 **프록시 객체를 기준으로 평가하므로 매칭되지 않습니다.**

### 3-1-5. args()

메서드 실행 시 전달되는 매개변수을 기준으로 JoinPoint를 선택합니다.

인자의 순서나 타입을 지정할 수 있고, 상위 타입으로 설정했을 경우 상속 타입의 인자도 허용됩니다. 
* 예) Number ➡️ Integer, Double 등

세밀한 범위 지정을 위해 다른 Pointcut과 사용됩니다.

* 예제들 

```java
@Pointcut("execution(* *(..)) && args(String)")
public void methodWithOneStringArg() {}
```

인자가 하나이고 그 타입이 String인 메서드에 매칭됩니다.

```java
@Pointcut("execution(* *(..)) && args(String, int)")
public void methodWithStringAndIntArgs() {}
```
인자가 정확히 두 개 있으며, 첫 번째 인자는 String, 두 번째 인자는 int인 메서드에 매칭됩니다.

```java
@Pointcut("execution(* *(..)) && args(String, ..)")
public void methodWithFirstArgString() {}
```

첫 번째 인자가 String 타입이고, 그 뒤에 오는 인자들은 개수나 타입에 관계없이 매칭됩니다.

### 3-1-6. @annotation()

메서드에 특정 어노테이션이 붙어 있는지를 기준으로 Join Point를 선택합니다.

특정 어노테이션이 선언된 메서드에만 Advice를 적용하고 싶을 때 사용합니다.

* 예제들

```java
// 사용자 정의 어노테이션
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.TYPE})
public @interface Trackable {
    String value() default "";
}

@Trackable("successfulMethod")
public void successfulMethod(String msg) {}
```

```java
@Pointcut("@annotation(trackable)")
public void annotationTrackable(Trackable trackable) {}
```

메서드 선언부에 @trackable 어노테이션이 붙은 메서드만을 대상으로 Join Point를 선택합니다.

### 3-1-7. @within()

클래스 수준에서 특정 어노테이션이 붙은 타입 내부의 모든 Join Point를 선택합니다.

클래스에 어노테이션이 붙어 있으면, 그 클래스 내에 정의된 모든 메서드에 Advice를 적용할 때 사용합니다.

* 예제들

```java
@Service
public class PaymentService {
    public void processPayment(double amount) {
        System.out.println("Processing payment: " + amount);
    }
}
```


```java
@Pointcut("@within(org.springframework.stereotype.Service)")
public void serviceClasses() {}
```

@Service 어노테이션이 붙은 클래스 내부의 모든 메서드을 대상으로 Join Point를 선택합니다.

### 3-1-8. @target()

Advice가 적용되는 시점에 실제 객체가 특정 어노테이션을 가지고 있는지를 기준으로 Join Point를 선택합니다.

타깃 객체가 특정 어노테이션을 가지고 있을 때만 Advice를 적용하고자 할 때 사용합니다.

@within()의 차이는 실제 객체를 가르킨다는 점입니다.

* 예제

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MyAnnotation {}

public class BaseService {
  public void doWork() {
    System.out.println("BaseService doing work");
  }
}

@Component
@MyAnnotation
public class DerivedService extends BaseService {
  @Override
  public void doWork() {
    System.out.println("DerivedService doing work");
  }
}
```

위 DerivedService는 인터페이스를 구현하고 있으므로 JDK 동적 프록시로 생성됩니다.

```java
@Pointcut("@within(com.example.annotation.MyAnnotation)")
public void annotatedWithin() {}
```

@within은 클래스 선언부에 어노테이션이 있는지를 기준으로 합니다.

DerivedService는 BaseService 프록시로 생성될 떄 어노테이션이 붙어있지 않습니다.

그러므로 매칭되지 않습니다.

```java
@Pointcut("@target(com.example.annotation.MyAnnotation)")
public void annotatedTarget() {}
```

@target은 실제 객체를 가르킵니다. DerivedService 가 실행시 올바르게 매칭됩니다.

JDK 동적 프록시로 생성되어도,

### 3-1-9. @args()

메서드 호출 시 전달된 파라미터의 어노테이션 정보를 기준으로 Join Point를 선택합니다.

* 예제

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Validated {}

@Service
public class UserService {

  // 첫 번째 파라미터에 @Validated 어노테이션 적용
  public void createUser(@Validated String username, int age) {
    System.out.println("Creating user: " + username + ", age: " + age);
  }

  // 두 번째 메서드: 두 번째 파라미터에 @Validated 어노테이션 적용
  public void updateUser(String username, @Validated String email) {
    System.out.println("Updating user: " + username + ", email: " + email);
  }
}
```


```java
@Pointcut("execution(* com.example.service.UserService.*(..)) && @args(com.example.annotation.Validated, ..)")
    public void firstArgValidated() {}
```

첫 번째 인자가 @Validated 어노테이션을 가지고 있어야 하며, 그 뒤로 매개변수는 어떤 어노테이션이든 상관없이 허용합니다.

```java
@Pointcut("execution(* com.example.service.UserService.*(..)) && @args(.., com.example.annotation.Validated, ..)")
public void anyArgValidated() {}
```

어느 위치에든 @Validated 어노테이션이 붙은 인자가 한 개 이상 있으면 매칭됩니다.

# 4. Advice 종류

1. @Before : 대상 메서드 호출 전에 실행된다.
2. @After : 대상 메서드 호출 후에 사용된다.
3. @AfterReturning: 대상 메서드가 정상 종료되었을 때 실행된다.
4. @AfterThrowing : 대상 메서드가 예외를 던졌을 때 실행된다.
5. @Around: 대상 메서드 실행 전후에 모두 개입할 수 있으며, proceed()를 통해 실행 제어가 가능하다

## 4-1. Advice 예시

Advice는 AOP에서 비즈니스 로직(Join Point)에 부가적인 기능을 적용하기 위한 코드입니다.

주로 5가지 종류의 Advice를 사용합니다.

1. @Before
2. @After 
3. @AfterReturning 
4. @AfterThrowing 
5. @Around

### 4-1-1. @Before

동작 시점: 대상 메서드 호출 이전에 실행됩니다.
용도 : 메서드 실행 전에 사전 작업을 수행할 때 사용합니다.

```java
// com.example.service 패키지 내 모든 메서드마다 호출 전에 실행
@Before("execution(* com.example.service..*(..))")
public void beforeAdvice(JoinPoint jp) {
    System.out.println("[@Before] Before executing: " + jp.getSignature());
}
```

### 4-1-2. @After

동작 시점: 대상 메서드 호출 이후에 실행됩니다. (정상 실행이든 예외 발생이든 관계없이 실행됩니다.)

용도: 메서드 호출 후 부가 작업을 수행할 때 사용합니다.

```java
// com.example.service 패키지 내 모든 메서드마다 호출 후에 실행
  @After("execution(* com.example.service..*(..))")
  public void afterAdvice(JoinPoint jp) {
      System.out.println("[@After] After executing: " + jp.getSignature());
  }
```

### 4-1-3. @AfterReturning

동작 시점: 대상 메서드가 정상적으로 종료된 후 실행됩니다.

용도: 메서드의 반환 값을 활용하여 후처리 작업을 수행할 때 사용합니다.

```java
// 메서드 호출 후, 정상 반환된 경우 실행, 반환값을 받아 처리할 수 있음.
@AfterReturning(pointcut = "execution(* com.example.service..*(..))", returning = "result")
public void afterReturningAdvice(JoinPoint jp, Object result) {
    System.out.println("[@AfterReturning] Method " + jp.getSignature() + " returned: " + result);
}
```

### 4-1-4. @AfterThrowing

동작 시점: 대상 메서드에서 예외가 발생한 경우 실행됩니다.

용도: 예외 발생 시 추가 작업을 수행할 때 사용합니다.

```java
// 메서드 호출 중 예외가 발생하면 실행, 발생한 예외를 인자로 받아 처리할 수 있음.
@AfterThrowing(pointcut = "execution(* com.example.service..*(..))", throwing = "ex")
public void afterThrowingAdvice(JoinPoint jp, Throwable ex) {
    System.out.println("[@AfterThrowing] Exception in " + jp.getSignature() + ": " + ex);
}
```

### 4-1-5. @Around

동작 시점: 대상 메서드 호출 전후 모두에 개입할 수 있으며, 메서드 실행을 직접 제어할 수 있습니다.
용도: 메서드 실행 전후에 공통 기능을 수행하거나, 실행 흐름을 제어할 때 사용합니다.

* ProceedingJoinPoint를 사용하여 대상 메서드의 실행을 시작하거나, 결과를 받아와서 가공할 수 있습니다.
* 
```java
 @Around("execution(* com.example.service..*(..))")
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        // 시작 시간 측정
        long startTime = System.currentTimeMillis();
        System.out.println("[@Around] Before executing: " + pjp.getSignature());

        // 원래 인자들을 가져옴 (필요 시 변환 가능)
        Object[] originalArgs = pjp.getArgs();
        System.out.println("Original arguments: " + Arrays.toString(originalArgs));

        // 예시: 매개변수 변환 (예: 문자열 인자를 모두 대문자로 변환)
        Object[] modifiedArgs = Arrays.copyOf(originalArgs, originalArgs.length);
        for (int i = 0; i < modifiedArgs.length; i++) {
            if (modifiedArgs[i] instanceof String) {
                modifiedArgs[i] = ((String) modifiedArgs[i]).toUpperCase();
            }
        }
        System.out.println("Modified arguments: " + Arrays.toString(modifiedArgs));

        Object result = null;
        try {
            // 대상 메서드 실행: modifiedArgs를 사용하면 매개변수로 변경된 값이 전달됨
            result = pjp.proceed(modifiedArgs);
            System.out.println("[@Around] After executing successfully: " + pjp.getSignature());
        } catch (Throwable ex) {
            System.out.println("[@Around] Exception thrown in: " + pjp.getSignature());
            // 예외 발생 시 추가 작업(예: 로깅, 알림 등) 수행 후 예외 재발생
            throw ex;
        }
        
        // 종료 시간 측정 후 실행 시간 출력
        long endTime = System.currentTimeMillis();
        System.out.println("[@Around] Execution time: " + (endTime - startTime) + " ms");
        
        // 예시: 반환값 변환 (예: 반환값이 String인 경우에 접두어 추가)
        if (result instanceof String) {
            result = "Modified Result: " + result;
        }
        
        return result;
    }
```

# 5. Join Point와 ProceedingJoinPoint

* JoinPoint : AOP가 적용될 수 있는 실행 시점, 메서드 호출 시점의 정보를 제공합니다.
  * 예시) 메서드 이름, 인자, 대상 객체 등
* ProceedingJoinPoint : 
  * Join Point의 하위 인터페이스로, @Around Advice에만 사용된다. 
  * 주로 메서드의 실행 시점이나 매개변수 변경에 사용된다.
  * 예시) proceed를 통해 테스트 로깅 시간, 매개변수 타입 변환 및 판별 등

# 6. AOP의 적용과 한계

* AOP 적용: 주로 로깅, 트랜잭션 관리, 보안 검사 등 공통 관심사를 핵심 비즈니스 로직과 분리하기 위해 사용됩니다.
* 내부 호출(self-invocation) 문제: 동일 클래스 내에서 **메서드를 직접 호출하면** 프록시를 거치지 않아 
AOP가 적용되지 않는 한계가 있습니다.
  * 해결 방법: @EnableAspectJAutoProxy(exposeProxy = true)와 AopContext.currentProxy() 활용.
  * @EnableAspectJAutoProxy(exposeProxy = true) 
    * AOP 프록시가 현재 실행 중인 프록시를 AopContext에 노출시킵니다.
    * 내부 메서드 호출 시에도 현재 프록시 객체를 직접 참조할 수 있게 합니다.
  * AopContext.currentProxy()
    * 직접 호출하여 현재 실행 중인 프록시 객체를 가져올 수 있습니다.ㅋ!

# 7. 프록시 생성 방식 (JDK 동적 프록시 vs CGLIB)
* JDK 동적 프록시: 인터페이스를 구현한 경우에 사용됩니다.
  * 대상 객체가 인터페이스를 구현해야 합니다.
* CGLIB: 기본 클래스일 경우 사용됩니다. 
  * 대상 객체의 하위 클래스를 동적으로 생성하여 프록시로 사용합니다.
  * final 메서드나 final 클래스는 프록시 생성에 제약이 있습니다.