package kr.ac.kumoh.ordersystem.domain;

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
    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] img;
    @Enumerated(EnumType.STRING)
    private MenuType type;

    public Menu(Integer id){
        this.id = id;
    }

}