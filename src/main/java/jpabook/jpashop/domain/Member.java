package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded // 내장 타입을 포함했다.
    private Address address;

    @OneToMany // 멤버 하나(일)에 여러 개의 오더(다)가 포함되므로 -> 일대다 관계이다
    private List<Order> orders = new ArrayList<>();
}
