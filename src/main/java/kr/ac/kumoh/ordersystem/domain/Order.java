package kr.ac.kumoh.ordersystem.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    @Column(name = "order_time")
    private LocalDateTime orderTime;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @Builder.Default
    private List<OrderMenu> orderMenus = new ArrayList<>();

    @Transient
    public void setOrderTime(){
        this.orderTime = LocalDateTime.now();
    }

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

    // price = 주문 총 가격
    public static void updateOrderPrice(OrderMenu menu, int price) {

        if (menu.getMenu().getDiscountType() == DiscountType.FixedRate && price >= 15000) {
            menu.setOrderPrice((int) (menu.getMenu().getPrice() - (menu.getMenu().getPrice() * 0.1)));
        } else if (menu.getMenu().getDiscountType() == DiscountType.FixedQuantity && price >= 10000) {
            menu.setOrderPrice(menu.getMenu().getPrice() - 1000);
        } else if (menu.getMenu().getDiscountType() == DiscountType.Time) {
            int time = menu.getOrderTime().getHour();
            if (time <= 11) {
               menu.setOrderPrice(menu.getMenu().getPrice() - 1000);
            }
        }
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
