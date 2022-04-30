package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // springframework에서 제공되는 걸로 사용한다. 데이터 변경이 없는, 읽기 전용 메서드에는 readonly를 적용해준다. 영속성 컨텍스트를 플러쉬하지 않으므로, 약간의 성능 향상.
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    // 회원가입
    @Transactional // 여기서는 write 권한도 필요하므로, 따로 또 설정해준다. 여기서 설정된 사항이 클래스 단위로 적용된 Transactional보다 우선적으로 적용된다.
    public Long join(Member member){
        validateDuplicateMember(member); // 문제가 있으면, 함수에서 EXCEPTION을 만들어낸다.
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        // EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMember(){
        return memberRepository.findAll();
    }

    // 회원 단건 조회
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

}
