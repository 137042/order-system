package kr.ac.kumoh.ordersystem.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "\"order\"")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @Builder.Default
    private List<OrderMenu> orderMenus = new ArrayList<OrderMenu>();


    @Transient
    public void setStatus(OrderStatus status){
        this.status = status;
    }
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }
    public void addOrderMenu(OrderMenu orderMenu){
        orderMenus.add(orderMenu);
        orderMenu.setOrder(this);
    }

    // 생성 메소드
    public static kr.ac.kumoh.ordersystem.domain.Order createOrder(Member member, OrderMenu... orderItems){
        kr.ac.kumoh.ordersystem.domain.Order order = new kr.ac.kumoh.ordersystem.domain.Order();
        order.setMember(member);
        Arrays.stream(orderItems).forEach(order::addOrderMenu);
        order.setStatus(OrderStatus.ORDERED);
        return order;
    }

    public void cancel(){
        if(status == OrderStatus.DELIVERED){
            throw new IllegalStateException("이미 배송완료된 상품은 취소 불가");
        }
        this.setStatus(OrderStatus.REJECTED);
    }

    public int getTotalPrice(){
        int totalPrice = orderMenus.stream().mapToInt(OrderMenu::getTotalPrice).sum();
        return totalPrice;
    }
}
