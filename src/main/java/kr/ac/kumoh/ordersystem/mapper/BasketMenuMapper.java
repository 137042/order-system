package kr.ac.kumoh.ordersystem.mapper;

import kr.ac.kumoh.ordersystem.domain.Order;
import kr.ac.kumoh.ordersystem.domain.OrderMenu;
import kr.ac.kumoh.ordersystem.dto.BasketMenuRes;
import kr.ac.kumoh.ordersystem.dto.OrderMenuRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BasketMenuMapper {

    public List<BasketMenuRes> toBasketMenuResList(Order basket, List<OrderMenu> orderMenuResList)
    {
        List<BasketMenuRes> basketMenuRes = new ArrayList<>();
        for(OrderMenu orderMenu : basket.getOrderMenus())
        {
            basketMenuRes.add(BasketMenuRes.builder()
                    .orderId(basket.getId())
                    .orderStatus(basket.getStatus())
                    .menuId(orderMenu.getMenu().getId())
                    .menuName(orderMenu.getMenu().getName())
                    .orderPrice(orderMenu.getOrderPrice())
                    .potatoCount(orderMenu.getPotatoCount())
                    .colaCount(orderMenu.getColaCount())
                    .singleCount(orderMenu.getSingleCount())
                    .setCount(orderMenu.getSetCount())
                    .build());
        }
        return basketMenuRes;
    }
}
