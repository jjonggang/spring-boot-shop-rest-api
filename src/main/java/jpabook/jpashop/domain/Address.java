package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable // JPA의 내장타입 -> 어디든 embed될 수 있다.
@Getter
@Setter
public class Address {
    private String city;
    private String street;
    private String zipcode;
}
