package kr.ac.kumoh.ordersystem.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "store")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String address;
    private String tel;

    @Column(name = "open_time")
    private LocalDateTime openTime;

    @Column(name = "close_time")
    private LocalDateTime closeTime;

    public Store(int id){
        this.id = id;
    }

}
