package kr.ac.kumoh.ordersystem.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Store {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Address address;
    private String tel;
    @Column(name = "OPEN_TIME")
    private LocalDateTime openTime;
    @Column(name="CLOSE_TIME")
    private LocalDateTime closeTime;
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<Order>();
}
