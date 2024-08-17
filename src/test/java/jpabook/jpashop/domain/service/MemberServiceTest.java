package jpabook.jpashop.domain.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.repository.MemberRepository;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;


    @Test
    void join() {

        Member member = new Member();
        member.setName("신선호");
        member.setAddress(new Address("city", "street", "zipCode"));

        Long memberId = memberService.join(member);

        Member findMember = memberRepository.findOne(memberId);

        assertThat(memberId).isEqualTo(findMember.getId());
    }


    @Test
    @DisplayName("중복 오류 테스트")
    void join2() {

        Member member = new Member();
        member.setName("신선호2");
        member.setAddress(new Address("city", "street", "zipCode"));

        memberService.join(member);

        assertThrows(IllegalStateException.class, () -> memberService.join(member));
    }
}