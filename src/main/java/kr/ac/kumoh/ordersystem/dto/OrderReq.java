package kr.ac.kumoh.ordersystem.dto;

import kr.ac.kumoh.ordersystem.domain.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderReq {

    private Integer orderId;
    private OrderStatus status;
    private Integer storeId;
    private Integer memberId;

}
