package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne // 여러개의 오더(다)가 멤버 하나(일)에 들어간다 -> 다대일 관계
    @JoinColumn(name = "member_id") // foreign 키 이름. 연관관계의 주인은 반드시 많은 쪽(order)이다. 많은 쪽에 항상 외래키가 존재한다.
    private Member member;
}
