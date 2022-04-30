package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.excetion.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// Single table 전략으로 한 테이블에 다 넣어버린다.
@DiscriminatorColumn(name = "dtype")
@Getter
@Setter
public abstract class Item {
    @Id
    @GeneratedValue
    @Column(name="item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //==비즈니스 로직==//
    // 데이터를 가지고 있는 쪽에 비지니스 로직이 있어야 응집력이 있다.
    // stock quantity 값을 변경할 일이 있으면, Setter를 사용해서 직접적으로 변경할 것이 아니라, 핵심 비지니스 메서드를 가지고 변경을 해야한다.

    // 재고 수량 증가
    public void addStock(int quantity){
       this.stockQuantity += quantity;
    }

    // 재고 감소
    public void removeStock(int quantity){
        int restStock = this.stockQuantity - quantity;
        if(restStock < 0) {
            throw new NotEnoughStockException("need more stock"); // Exception을 새로 만들어준다.
        }
        this.stockQuantity = restStock;
    }
}
