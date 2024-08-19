# 스프링 부트와 JPA 활용

Thymeleaf

장점 : html 순수 템플릿

단점 : 닫는 테그가 없으면 오류가 난다. 성능 이슈. 근데 3.0 이상은 극복

# 도메엔 분석 설계

외래키가 있는 곳에 연관관계 매핑을 해라.

단지 외래 키를 관리하는 주인이라는 의미이지 비즈니스상 우위에 있다는 의미는 아니다.

반대로 한다면 외래키가 수정될 경우 코드를 작성(수정)하기 힘들다.

OnetoOne인 경우 양쪽에 외래키 어느곳에서 관리해도 된다.

그러나 자주 조회되는 쪽에 두는 것이 좋다.

* 상속 세가지 설정
    1) @Inheritance(strategy = InheritanceType.SINGLE_TABLE)

    2) TABLE_PER_CLASS

    3) JOINED

* @DiscriminatorColumn, @DiscriminatorValue
  @DiscriminatorColumn(name = "dtype")

* @ManytoMany
  @ManytoMany는 싦무에서 쓰지말자. 시간, 작성자 등등 여러 필드 들어가기 위한 수정이 안된다.

* @Embeddable
  // @Embeddable는 public 또는 protected로 설정해야 한다. (JPA 규약)
  // default 나 private으로 만들지 못하게 한 이유는 JPA 구현 라이브러리가 객체를 생성할 때 리플랙션 같은 기술을 사용할 수 있게 지원해야 하기 때문이다.
  // 다른곳에서 생성하지 않게 안전하게 protected로 설정한다.

** 컬렉션은 필드에서 초기화 하자.

1) null 문제가 발생하지 않는데
2) 하이버네이트는 엔티티를 영속화할 때, 컬렉션을 감싸서 하이버네이트가 제공하는 내장 컬렉션으로 변경한다. 만약 getOrders() 처럼 임의의 메서드에서 컬렉션을 잘못 생성하면 하이버네이트 내부 매커니즘에
   문제가 발생할 수 있다.

```java
class Test {

    @Test
    void test() {
        Member member = new Member();
        System.out.println(member.getOrders().getClass());
        em.persist(member);
        System.out.println(member.getOrders().getClass());
    }
}

//출력 결과
//class java.util.ArrayList
//class org.hibernate.collection.internal.PersistentBag

```

** 스프링부트 하이버네이트 기본 매핑 전략을 변경해서 실제 테이블 필드명은 다름

카멜케이스 -> 언더스코어

.(점) -> 언더스코어

대문자 -> 소문자



---

# 변경 감지와 병합

스프링은 변경 감지를 추천한다. (실무에서도...)

결론부터 얘기하면 엔티티를 변경할 때에는 항상 변경 감지 사용해라.

### 준영속 엔티티

영속성 컨텍스트가 더는 관리하지 않는 엔티티를 말한다.

이미 db에 한번 갔다와서 식별자를 가지고 온 객체

개발자가 임의로 식별자를 넣어줘도 준영속 엔티티이다.

### 병합

<img width="910" alt="Screenshot 2024-08-17 at 21 53 28" src="https://github.com/user-attachments/assets/33d13011-6c89-4e0b-aabc-f56234dc0c99">

##### 병합 순서

1) 준영속 엔티티(병합하려는 엔티티)에 맞는 엔티티가 있는지 영속성 컨텍스트(1차캐시)에서 찾는다.

2) 없다면 db에서 조회해서 영속성 컨텍스트에 넣는다.

3) 영속성 컨텍스트에 있는 엔티티와 준영속 엔티티를 병합한다.

4) 트랜잭션이 끝나면 커밋.

**변경 감지는 내가 원하는 속성만 변경되지만, 병합은 모든 속성이 변경된다.**

**따라서 병합 시에 데이터에 null이 있을 경우 문제가 생길 수 있다.**






























