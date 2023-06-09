package kr.ac.kumoh.ordersystem.mapper;


import kr.ac.kumoh.ordersystem.domain.Order;
import kr.ac.kumoh.ordersystem.dto.OrderCancelRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderCancelMapper {
    private final OrderMenuMapper orderMenuMapper;
    public OrderCancelRes toOrderCancleRes(Order order, boolean success){
        if(order == null)
            return null;
        OrderCancelRes.OrderCancelResBuilder orderCancelResBuilder = OrderCancelRes.builder();
        return orderCancelResBuilder
                .orderId(order.getId())
                .orderStatus(order.getStatus())
                .cancelSuccess(success)
                .build();
    }
}
