package jpabook.jpashop;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;


    @Test
    @Transactional
    @Rollback(value = false)
    void saveMember() {

        Member member = new Member();
        member.setUsername("memberA");

        Long saveId = memberRepository.saveMember(member);

        Member findMember = memberRepository.findMember(saveId);

        assertThat(findMember.getId()).isEqualTo(saveId);

        System.out.println(findMember.getId());

        assertThat(findMember).isEqualTo(member);
    }

}