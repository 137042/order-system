package kr.ac.kumoh.ordersystem.dto;

import kr.ac.kumoh.ordersystem.domain.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCancelReq {

    private Integer orderId;
    private OrderStatus status;
}
