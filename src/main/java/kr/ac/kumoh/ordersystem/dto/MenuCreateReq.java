package kr.ac.kumoh.ordersystem.dto;

import kr.ac.kumoh.ordersystem.domain.MenuType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuCreateReq {

    @NotNull
    private String name;
    @NotNull
    private Integer price;
    @NotNull
    private String description;
    @NotNull
    private String img;
    @NotNull
    private MenuType type;
}