package kr.ac.kumoh.ordersystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderMenuRes {

    private String menuName;
    private int orderPrice;
    private int count;

}
