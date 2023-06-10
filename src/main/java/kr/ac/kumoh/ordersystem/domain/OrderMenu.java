package kr.ac.kumoh.ordersystem.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

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

    @Column(name = "potato_count")
    @ColumnDefault(value = "0")
    private Integer potatoCount;

    @Column(name = "cola_count")
    @ColumnDefault(value = "0")
    private Integer colaCount;

    @Column(name = "single_count")
    @ColumnDefault(value = "0")
    private Integer singleCount;

    @Column(name = "set_count")
    @ColumnDefault(value = "0")
    private Integer setCount;

}
