package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    // order와 member는 다대일 관계
    @ManyToOne(fetch = FetchType.LAZY)
    // mapping을 뭘로 할지를 명시해준다.
    // 양방향 참조가 일어났다. foreign key는 단 하나이므로, 연관관계의 주인을 정해줘야 한다.
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // Order가 persist될 때 orderItem도 같이 persist된다.
    private List<OrderItem> orderItems = new ArrayList<>();
    // orderItems에다 아이템을 저장하고, Order를 저장하면, orderItems도 같이 저장이 된다.
    // CascadeType.All을 하면, 자연스럽게 persist를 전파한다.

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // Order가 persist될 때 delivery도 persist 된다.
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; // 주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문상태 [ORDER, CANCEL]

    //==연관관계 메서드==// 핵심적인 컨트롤을 수행하는 곳에 놓는 것이 좋다.
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }
    // 양방향일 때 원자적으로 한 코드로 다 해결해버린다.


    //==생성메서드==//
    // 주문 생성에 대한 복잡한 비즈니스 로직을 createOrder에서 전부 해결한다.
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems){
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //==비즈니스 로직==//
    /**
     * 주문취소
     */
    public void cancel(){
        if(this.delivery.getStatus() == DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다. ");
        }

        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem: this.orderItems){ // 전체 아이템들을 돌면서 재고 수량을 다시 올려준다.
            orderItem.cancel();
        }
    }

    //==조회로직==//
    // 전체 주문 가격 조회
    public int getTotalPrice(){
        return orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();
//        for (OrderItem orderItem : orderItems){
//            totalPrice += orderItem.getTotalPrice();
//        }

    }
}
