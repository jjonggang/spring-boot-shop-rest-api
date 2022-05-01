package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;


    //주문
    @Transactional // 데이터를 변경하기 때문에
    public Long order(Long memberId, Long itemId, int count){
        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // 주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count); // static 생성 메서드를 사용해 생성

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem); // static 생성 메서드를 사용해 생성

        // 주문 저장
        orderRepository.save(order); // order만 저장해도, orderItem과 delivery가 함께 persist 됐다. cascade 옵션 때문에
        // orderItem과 delivery는 order만 사용한다. 그러므로, order 안에서 가급적 해결되도록 cascade를 건다. 하지만 다른데서도 많이 사용되는
        // 경우에는 cascade 사용을 주의하자.
        return order.getId();
    }

    //취소


    //검색


}
