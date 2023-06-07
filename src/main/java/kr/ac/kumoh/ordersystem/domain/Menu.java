package kr.ac.kumoh.ordersystem.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class Menu {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private int price;
    private String description;
    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] img;
    @Enumerated(EnumType.STRING)
    private MenuType type;
}
