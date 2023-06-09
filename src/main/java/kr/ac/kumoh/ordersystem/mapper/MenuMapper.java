package kr.ac.kumoh.ordersystem.mapper;

import kr.ac.kumoh.ordersystem.domain.Menu;
import kr.ac.kumoh.ordersystem.dto.MenuCreateReq;
import kr.ac.kumoh.ordersystem.dto.MenuReq;
import kr.ac.kumoh.ordersystem.dto.MenuRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MenuMapper {
    public MenuRes toDto(Menu menu){
        MenuRes builder = MenuRes.builder()
                .id(menu.getId())
                .name(menu.getName())
                .price(menu.getPrice())
                .description(menu.getDescription())
                .img(menu.getImg())
                .type(menu.getType())
                .discountType(menu.getDiscountType())
                .build();
        return builder;
    }

    public Menu toEntity(MenuReq req){
        Menu.MenuBuilder builder = Menu.builder();
        builder
                .id(req.getId())
                .name(req.getName())
                .price(req.getPrice())
                .description(req.getDescription())
                .img(req.getImg())
                .type(req.getType());

        return builder.build();
    }

    public Menu toCreateEntity(MenuCreateReq req){
        Menu.MenuBuilder builder = Menu.builder();
        builder
                .name(req.getName())
                .price(req.getPrice())
                .description(req.getDescription())
                .img(req.getImg())
                .type(req.getType());

        return builder.build();
    }
}