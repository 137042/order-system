package kr.ac.kumoh.ordersystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import kr.ac.kumoh.ordersystem.domain.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderRes {

    private Integer orderId;
    private OrderStatus orderStatus;
    private LocalDateTime orderTime;
    private Integer totalPrice;
    private List<OrderMenuRes> orderMenuResList;

}
