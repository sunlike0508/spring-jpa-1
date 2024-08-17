package jpabook.jpashop.domain.repository;


import java.util.List;
import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    //Autowired // spring date jpa에서 여기 안에 @PersistenceContext을 지원
    //private EntityManager em;


    public void save(Member member) {
        em.persist(member);
    }


    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }


    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }


    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member  m where m.name = :name", Member.class).setParameter("name", name)
                .getResultList();
    }


    // 원칙적으로는 void가 맞음. 커맨드성 메소드 분리
    // 그래도 id 정도만 반환해서 다시 조회할 수 있게 해줌
    //    public Long saveMember(Member member) {
    //        em.persist(member);
    //        return member.getId();
    //    }
    //
    //
    //    public Member findMember(Long id) {
    //        return em.find(Member.class, id);
    //    }
}
