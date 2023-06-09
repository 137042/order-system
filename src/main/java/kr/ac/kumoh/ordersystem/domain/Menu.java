package kr.ac.kumoh.ordersystem.domain;

//import kr.ac.kumoh.ordersystem.dto.MenuReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private Integer price;
    private String description;
    private String img;
    @Enumerated(EnumType.STRING)
    private MenuType type;
    @Enumerated(EnumType.STRING)
    private DiscountType discountType;

    public Menu(Integer id){
        this.id = id;
    }

//    public void updateMenu(MenuReq menu){
//        this.name = menu.getName();
//        this.price = menu.getPrice();
//        this.description = menu.getDescription();
//        this.img = menu.getImg();
//        this.type = menu.getType();
//    }

    public void updateDiscountType(DiscountType discountType){
        this.discountType = discountType;
    }
}
