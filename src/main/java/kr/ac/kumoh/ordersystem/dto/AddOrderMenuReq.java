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

    private Integer memberId;
    private Integer menuId;
    private Integer storeId;
    private int orderPrice;
    private int potatoCount;
    private int colaCount;
    private int singleCount;
    private int setCount;
}
