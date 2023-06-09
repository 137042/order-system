package kr.ac.kumoh.ordersystem.mapper;

import kr.ac.kumoh.ordersystem.domain.Menu;
import kr.ac.kumoh.ordersystem.domain.Order;
import kr.ac.kumoh.ordersystem.domain.OrderMenu;
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
                .count(orderMenuReq.getCount())
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
                .count(orderMenu.getCount())
                .build();
    }

    public OrderMenuCountRes toOrderMenuCountRes(Integer menuId, String name, Integer count){
        OrderMenuCountRes builder = OrderMenuCountRes.builder()
                .menuId(menuId)
                .name(name)
                .count(count)
                .build();
        return builder;

    }
}
