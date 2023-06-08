package kr.ac.kumoh.ordersystem.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_menu")
public class OrderMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @Column(name = "order_time")
    private LocalTime orderTime;

    @Column(name = "order_price")
    private Integer orderPrice;

    private Integer count;


    // 생성 메소드
    public static kr.ac.kumoh.ordersystem.domain.OrderMenu createOrderItem(Menu menu, int orderPrice, int count) {
        kr.ac.kumoh.ordersystem.domain.OrderMenu orderMenu = new kr.ac.kumoh.ordersystem.domain.OrderMenu();
        orderMenu.setMenu(menu);

        orderMenu.setOrderPrice(orderPrice);
        orderMenu.setCount(count);
        return orderMenu;
    }

    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
