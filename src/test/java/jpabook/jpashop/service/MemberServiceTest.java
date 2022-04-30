package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional // 테스트에 붙으면 롤백해버린다.
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception{
        // given
        Member member = new Member();
        member.setName("kim");

        // when
        Long savedId = memberService.join(member);


        // then
        Assert.assertEquals(member, memberRepository.findOne(savedId));

    }

    @Test(expected = IllegalStateException.class) // 해당 exception이 발생해야한다.
    public void 중복_회원_예외() throws Exception{
        // given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        // when
        memberService.join(member1);
        memberService.join(member2);
//        try{
//            memberService.join(member2); // 예외가 발생해야 한다.
//
//        }catch (IllegalStateException e){
//            return;
//        }

        // then
        Assert.fail("예외가 발생해야 한다."); // 여기까지 왔으면, 테스트에 실패했다는 의미

    }
}