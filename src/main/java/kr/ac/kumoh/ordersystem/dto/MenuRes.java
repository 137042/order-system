package kr.ac.kumoh.ordersystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import kr.ac.kumoh.ordersystem.domain.DiscountType;
import kr.ac.kumoh.ordersystem.domain.MenuType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuRes {

    private Integer id;
    private String name;
    private Integer price;
    private String description;
    private String img;
    private MenuType type;

    private DiscountType discountType;
}
