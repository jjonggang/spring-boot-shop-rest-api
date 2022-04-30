package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded // 내장타입을 포함했다는 의미
    private Address address;

    // 하나의 회원이 여러개의 상품을 주문하기 때문에, 일대다 관계가 된다.
    // 연관관계의 주인이 아니므로 누구에 의해서 mapping 됐는지를 명시해준다.
    @OneToMany(mappedBy = "member") // order table에 있는 member field에 의해서 매핑됐다.
    private List<Order> orders = new ArrayList<>(); // 컬렉션은 필드에서 바로 초기화하는 것이 안전하다.

}
