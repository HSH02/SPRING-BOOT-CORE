# 1. AOP 란? 

AOP(Aspect-Oriented Programming)은 애플리케이션의 공통 관심사를 모듈화하여 
핵심 비즈니스 로직과 분리하는 프로그래밍 기법이다.

* 장점: 코드 중복을 줄이고 유지보수성을 높일 수 있다.
* 단점:


# 2. AOP는 어떻게 작동할까

Spring AOP는 프록시 기반으로 동작한다.

* 대상 Bean이 생성된 후, 해당 Bean을 프록시 객체로 감싸고, 클라이언트는 프록시를 통해 호출한다.
* 프록시는 메서드 호출을 가로채서, 미리 정의된 Advice를 적용한 후 실제 대상 메서드를 호출한다.
* 프록시의 생성 방식은 JDK 동적 프록시와 CGLIB 방식이 있으며, 대상 Bean의 인터페이스 구현 여부에 따라 선택된다.

# 3. Pointcut

Pointcut은 Advice를 적용할 Join Point를 선택하는 표현식이다.
Joint Point는 Advice가 적용되는 지점을 뜻하며, Pointcut은 그것을 정의해준다.

* execution() : 메서드의 실행(반환 타입, 메서드 명, 패키지, 인자) 등을 기반으로 조건을 지정한다.
* within() : 특정 클래스나 패키지 내부의 모든 Join Point를 지정한다.
* this() : 프록시 객체의 타입을 기준으로 타겟팅 한다.
* target() : 런타임 대상 객체의 실제 타입에 어노테이션이 붙어 있는지를 기반으로 한다.
* args(): 메서드 인자의 타입을 기준으로 한다.
* @annotation() : 메서드 선언부에 붙은 어노테이션을 기준으로 한다.
* @within() : 클래스 선언부에 붙은 어노테이션을 기준으로, 해당 클래스 관련 Join Point를 선택한다.
* @target() : 런타임 대상 객체의 타입에 붙은 어노테이션을 기준으로 한다.
* @args() : 메서드 인자에 붙은 어노테이션 정보를 기준으로 한다.

## 3-1. Point 예시 



# 4. Join Point와 ProceedingJoinPoint

JoinPoint : AOP가 적용될 수 있는 실행 시점, 메서드 호출 시점의 정보를 제공한다.
* 예시) 메서드 이름, 인자, 대상 객체 등
ProceedingJoinPoint : Join Point의 하위 인터페이스로, @Around Advice에만 사용된다. 메서드의 실행 시점이나 매개변수 변경에 사용된다.
* 예시) proceed를 통해 테스트 로깅 시간, 매개변수 타입 변환 및 판별 등 

# 5. Advice 종류

1. @Before : 대상 메서드 호출 전에 실행된다.
2. @After : 대상 메서드 호출 후에 사용된다.
3. @AfterReturning: 대상 메서드가 정상 종료되었을 떄 실행된다.
4. @AfterThrowing : 대상 메서드가 예외를 던졌을 때 실행된다.
5. @Around: 대상 메서드 실행 전후에 모두 개입할 수 있으며, proceed()를 통해 실행 제어가 가능하다.

# 6. AOP의 적용과 한계

* AOP 적용: 주로 로깅, 트랜잭션 관리, 보안 검사 등 공통 관심사를 핵심 비즈니스 로직과 분리하기 위해 사용됩니다.
* 내부 호출(self-invocation) 문제: 동일 클래스 내에서 메서드를 직접 호출하면 프록시를 거치지 않아 
AOP 어드바이스가 적용되지 않는 한계가 있습니다.
  * 해결 방법: @EnableAspectJAutoProxy(exposeProxy = true)와 AopContext.currentProxy() 활용.

# 7. 프록시 생성 방식 (JDK 동적 프록시 vs CGLIB)
* JDK 동적 프록시: 인터페이스 기반으로 프록시를 생성합니다.
  * 대상 객체가 인터페이스를 구현해야 합니다.
* CGLIB: 클래스 기반의 프록시를 생성합니다.
  * 대상 객체의 하위 클래스를 동적으로 생성하여 프록시로 사용합니다.
  * final 메서드나 final 클래스는 프록시 생성에 제약이 있습니다.