package jpabook.jpashop;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;


    // 원칙적으로는 void가 맞음. 커맨드성 메소드 분리
    // 그래도 id 정도만 반환해서 다시 조회할 수 있게 해줌
    public Long saveMember(Member member) {
        em.persist(member);
        return member.getId();
    }


    public Member findMember(Long id) {
        return em.find(Member.class, id);
    }
}
