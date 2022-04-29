package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import jpabook.jpashop.domain.item.Item;

@Entity
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item",
    joinColumns = @JoinColumn(name = "category_id"), // 내 쪽에서 들어가는 거
            inverseJoinColumns = @JoinColumn(name = "item_id") // item 쪽에서 들어가는 거
    ) // 중간 테이블 mapping
    private List<Item> Items = new ArrayList<>();


    // 하나의 카테고리는 어떤 카테고리를 부모로 가질 수 있고.
    // 부모 카테고리는 자식 카테고리를 여러 개 가질 수 있다.
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

}
