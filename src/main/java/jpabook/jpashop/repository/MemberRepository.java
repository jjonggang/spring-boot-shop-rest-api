package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository // @Component가 포함돼있어서, 컴포넌트 스캔의 대상이 돼서, 스프링 빈으로 자동 등록 된다.
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Member member){
        em.persist(member); // persist를 하면, 영속성 컨텍스트에 멤버 객체를 넣는다. 트랜잭션이 커밋되는 시점에 DB에 반영된다.
    }

    public Member findOne(Long id){ // 단건 조회
        return em.find(Member.class, id); // 타입, PK
    }

    public List<Member> findAll(){ // JPQL사용, SQL문과 유사.
        // JPQL. SQL은 테이블을 대상으로 쿼리를 수행하는데, JPQL은 엔티티 객체를 대상으로 쿼리를 수행한다.
        return em.createQuery("select m from Member m", Member.class) // 조회 타입은 Member.class
                .getResultList();
    }

    public List<Member> findByName(String name){ // parameter binding을 통해 특정 이름의 회원만을 찾는다.
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }


}
