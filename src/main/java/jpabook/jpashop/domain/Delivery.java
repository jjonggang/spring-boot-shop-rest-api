package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Delivery {
    @Id
    @GeneratedValue
    @Column(name="delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery") // OneToOne인 경우에는 access가 많은 곳에 foreign key를 놓는다.
    private Order order;

    @Embedded
    private Address address;

    // enum 타입은 반드시 @Enumerate 어노테이션을 넣어야한다.
    @Enumerated(EnumType.STRING) // ORDINAL은 숫자로 들어감. 절대 사용 X String으로 사용하자.
    private DeliveryStatus status; // READY, COMP
}
