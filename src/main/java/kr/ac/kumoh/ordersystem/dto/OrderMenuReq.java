package kr.ac.kumoh.ordersystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderMenuReq {

    private Integer menuId;
    private int orderPrice;
    private int count;

}
