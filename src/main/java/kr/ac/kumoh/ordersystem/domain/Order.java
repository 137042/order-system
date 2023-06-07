package kr.ac.kumoh.ordersystem.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NamedEntityGraph(
        name = "order.member.graph",
        attributeNodes = {
                @NamedAttributeNode("member")
        }
)
@NamedEntityGraph(
        name = "orderWithMember.orderMenu.order.Graph",
        attributeNodes = {
                @NamedAttributeNode("member"),
                @NamedAttributeNode(value = "orderItems", subgraph = "orderMenu-subgraph")
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "orderMenu-subgraph",
                        attributeNodes = {
                                @NamedAttributeNode("menu")
                        }
                )
        }
)
@Entity
@Setter
@Getter
@Table(name="ORDER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STORE_ID")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderMenu> orderMenus = new ArrayList<OrderMenu>();

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
        Order order = new Order();
        order.setMember(member);
        Arrays.stream(orderItems).forEach(order::addOrderMenu);
        order.setStatus(OrderStatus.ORDER);
        return order;
    }

    public void cancel(){
        if(status == OrderStatus.COMPLETE){
            throw new IllegalStateException("이미 배송완료된 상품은 취소 불가");
        }
        this.setStatus(OrderStatus.CANCELED);
    }

    public int getTotalPrice(){
        int totalPrice = orderMenus.stream().mapToInt(OrderMenu::getTotalPrice).sum();
        return totalPrice;
    }
}
