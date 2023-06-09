package kr.ac.kumoh.ordersystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddOrderMenuReq {

    private Integer orderId;
//    private OrderStatus status;
//    private Integer storeId;
//    private Integer memberId;
//    private List<OrderMenuReq> orderMenuReqList;
    private Integer menuId;
    private int orderPrice;
    private int count;
}
