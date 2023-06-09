package kr.ac.kumoh.ordersystem.mapper;

import kr.ac.kumoh.ordersystem.domain.Member;
import kr.ac.kumoh.ordersystem.domain.Order;
import kr.ac.kumoh.ordersystem.domain.Store;
import kr.ac.kumoh.ordersystem.dto.OrderMenuRes;
import kr.ac.kumoh.ordersystem.dto.OrderReq;
import kr.ac.kumoh.ordersystem.dto.OrderRes;
import kr.ac.kumoh.ordersystem.repository.MenuRepository;
import kr.ac.kumoh.ordersystem.repository.OrderMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final OrderMenuMapper orderMenuMapper;
    private final OrderMenuRepository orderMenuRepository;

    public Order toOrder(OrderReq orderReq){
        if(orderReq == null)
            return null;

        Order.OrderBuilder orderBuilder = Order.builder();
        orderBuilder
                .status(orderReq.getStatus())
                .store(new Store(orderReq.getStoreId()))
                .member(new Member(orderReq.getMemberId()));

        if(orderReq.getOrderId() != null)
            return orderBuilder.id(orderReq.getOrderId()).build();
        return orderBuilder.build();
    }

    public OrderRes toOrderRes(Order order, List<OrderMenuRes> orderMenuResList){
        if(order == null || orderMenuResList == null)
            return null;
        OrderRes.OrderResBuilder orderResBuilder = OrderRes.builder();
        return orderResBuilder
                .orderId(order.getId())
                .orderStatus(order.getStatus())
                .orderTime(order.getOrderTime())
                .totalPrice(order.getTotalPrice())
                .orderMenuResList(orderMenuResList)
                .build();
    }

    public OrderRes toOrderRes(Order order){
        if(order == null)
            return null;
        OrderRes.OrderResBuilder orderResBuilder = OrderRes.builder();
        return orderResBuilder
                .orderId(order.getId())
                .orderStatus(order.getStatus())
                .orderTime(order.getOrderTime())
                .totalPrice(order.getTotalPrice())
                .build();
    }

    public OrderRes toOrderResWithOrderMenu(Order order){
        if(order == null)
            return null;
        OrderRes.OrderResBuilder orderResBuilder = OrderRes.builder();
        return orderResBuilder
                .orderId(order.getId())
                .orderStatus(order.getStatus())
                .orderTime(order.getOrderTime())
                .orderMenuResList(orderMenuMapper.toOrderMenuRes(order.getOrderMenus()))
                .build();

    }
}
