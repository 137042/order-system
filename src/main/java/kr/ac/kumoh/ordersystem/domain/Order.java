package kr.ac.kumoh.ordersystem.domain;

import kr.ac.kumoh.ordersystem.repository.MenuRepository;
import kr.ac.kumoh.ordersystem.repository.OrderMenuRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    private LocalTime orderTime;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @Builder.Default
    private List<OrderMenu> orderMenus = new ArrayList<>();

    @Transient
    public void setOrderTime(){
        this.orderTime = LocalTime.now();
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
    public static Order createOrder(Member member, OrderMenu... orderItems){
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

    public Integer getTotalPrice(OrderMenuRepository orderMenuRepository){
        int potato = 1700, cola = 1500;
        int discountAmount = 0, timeDiscountAmount = 0;
        double discountRate = 1, sum = 0;

        orderMenus = orderMenuRepository.findAllByOrder(this);
        for(OrderMenu orderMenu : orderMenus){
            if(orderMenu.getMenu().getDiscountType().equals(DiscountType.FixedRate))
                discountAmount += 1000;
            else if(orderMenu.getMenu().getDiscountType().equals(DiscountType.FixedQuantity))
                discountRate *= 0.9;
            else if(orderMenu.getMenu().getDiscountType().equals(DiscountType.Time)
            && orderTime.isBefore(LocalTime.of(11, 0)))
                timeDiscountAmount += 1000;

            sum = sum + orderMenu.getPotatoCount() * potato + orderMenu.getColaCount() * cola;
            sum = sum + orderMenu.getSingleCount()* orderMenu.getMenu().getPrice();
            sum = sum + orderMenu.getSetCount() * (orderMenu.getMenu().getPrice() + 3000);
        }

        if(sum > 15000)
            sum = sum * discountRate - discountAmount - timeDiscountAmount;
        else if(sum > 0)
            sum -= discountAmount - timeDiscountAmount;
        else
            sum -= timeDiscountAmount;

        return (int) sum;
    }

}
