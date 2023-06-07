package kr.ac.kumoh.ordersystem.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name="ORDER_MENU")
public class OrderMenu {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MENU_ID")
    private Menu menu;

    @Column(name = "ORDER_PRICE")
    private int orderPrice;
    private int count;

    // 생성 메소드
    public static OrderMenu createOrderItem(Menu menu, int orderPrice, int count){
        OrderMenu orderMenu = new OrderMenu();
        orderMenu.setMenu(menu);
        orderMenu.setOrderPrice(orderPrice);
        orderMenu.setCount(count);
        return orderMenu;
    }
    public int getTotalPrice(){
        return getOrderPrice()*getCount();
    }
}
