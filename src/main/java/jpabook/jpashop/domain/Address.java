package jpabook.jpashop.domain;

import javax.persistence.Embeddable;

@Embeddable // JPA의 내장 타입, 어딘가에 내장될 수 있다.
public class Address {
    private String city;
    private String street;
    private String zipcode;
}
