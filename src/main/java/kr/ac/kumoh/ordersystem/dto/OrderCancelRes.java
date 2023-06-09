package kr.ac.kumoh.ordersystem.dto;

import kr.ac.kumoh.ordersystem.domain.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCancelRes{
    private Integer orderId;
    private OrderStatus orderStatus;
    private LocalDateTime orderTime;
    private List<OrderMenuRes> orderMenuResList;
    private boolean cancelSuccess;
}
