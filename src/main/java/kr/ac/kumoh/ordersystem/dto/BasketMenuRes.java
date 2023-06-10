package kr.ac.kumoh.ordersystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import kr.ac.kumoh.ordersystem.domain.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BasketMenuRes {

    private int orderId;
    private OrderStatus orderStatus;
    private int menuId;
    private String menuName;
    private int orderPrice;
    private int potatoCount;
    private int colaCount;
    private int singleCount;
    private int setCount;

}
