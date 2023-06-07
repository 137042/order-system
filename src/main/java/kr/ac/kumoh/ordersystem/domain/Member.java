package kr.ac.kumoh.ordersystem.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Member {
    @Id @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE_TYPE")
    private MemberRoleType roleType;
    private String name;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<Order>();
}
