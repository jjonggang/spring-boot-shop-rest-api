package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable // JPA의 내장타입 -> 어디든 embed될 수 있다.
@Getter
public class Address {
    private String city;
    private String street;
    private String zipcode;

    // JPA 스펙상 만듬
    protected Address() {
    }

    // Setter대신 처음 생성할 때 생성자를 통해 만들어지도록 해서, 불필요한 값 수정이 없게 한다.
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
