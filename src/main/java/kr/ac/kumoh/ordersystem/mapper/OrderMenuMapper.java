package kr.ac.kumoh.ordersystem.mapper;

import kr.ac.kumoh.ordersystem.domain.Menu;
import kr.ac.kumoh.ordersystem.domain.Order;
import kr.ac.kumoh.ordersystem.domain.OrderMenu;
import kr.ac.kumoh.ordersystem.dto.AddOrderMenuReq;
import kr.ac.kumoh.ordersystem.dto.OrderMenuCountRes;
import kr.ac.kumoh.ordersystem.dto.OrderMenuReq;
import kr.ac.kumoh.ordersystem.dto.OrderMenuRes;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMenuMapper {

    public List<OrderMenu> toOrderMenu(Order order, List<OrderMenuReq> orderMenuReqList){
        if(orderMenuReqList == null)
            return null;
        return orderMenuReqList.stream().map(o -> toOrderMenu(order, o)).collect(Collectors.toList());
    }

    public OrderMenu toOrderMenu(Order order, OrderMenuReq orderMenuReq){
        if(orderMenuReq == null)
            return null;
        OrderMenu.OrderMenuBuilder orderMenuBuilder = OrderMenu.builder();
        return orderMenuBuilder
                .order(order)
                .menu(new Menu(orderMenuReq.getMenuId()))
                .orderPrice(orderMenuReq.getOrderPrice())
                .potatoCount(orderMenuReq.getPotatoCount())
                .colaCount(orderMenuReq.getSingleCount())
                .setCount(orderMenuReq.getSetCount())
                .singleCount(orderMenuReq.getSingleCount())
                .build();
    }

    public OrderMenu toOrderMenu(Order order, AddOrderMenuReq addOrderMenuReq){
        if(addOrderMenuReq == null)
            return null;
        OrderMenu.OrderMenuBuilder orderMenuBuilder = OrderMenu.builder();
        return orderMenuBuilder
                .order(order)
                .menu(new Menu(addOrderMenuReq.getMenuId()))
                .orderPrice(addOrderMenuReq.getOrderPrice())
                .potatoCount(addOrderMenuReq.getPotatoCount())
                .colaCount(addOrderMenuReq.getSingleCount())
                .setCount(addOrderMenuReq.getSetCount())
                .singleCount(addOrderMenuReq.getSingleCount())
                .build();
    }

    public List<OrderMenuRes> toOrderMenuRes(List<OrderMenu> orderMenuList){
        if(orderMenuList == null)
            return null;
        return orderMenuList.stream().map(this::toOrderMenuRes).collect(Collectors.toList());
    }

    public OrderMenuRes toOrderMenuRes(OrderMenu orderMenu){
        if(orderMenu == null)
            return null;
        OrderMenuRes.OrderMenuResBuilder orderMenuResBuilder = OrderMenuRes.builder();
        return orderMenuResBuilder
                .menuId(orderMenu.getMenu().getId())
                .orderPrice(orderMenu.getOrderPrice())
                .potatoCount(orderMenu.getPotatoCount())
                .colaCount(orderMenu.getSingleCount())
                .setCount(orderMenu.getSetCount())
                .singleCount(orderMenu.getSingleCount())
                .build();
    }

    public OrderMenuCountRes toOrderMenuCountRes(Menu menu, Integer count, Integer setCount){
        OrderMenuCountRes builder = OrderMenuCountRes.builder()
                .menuId(menu.getId())
                .count(count)
                .setCount(setCount)
                .total(100)
                .build();
        return builder;

    }
}
