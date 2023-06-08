package kr.ac.kumoh.ordersystem.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "member")
public class Member {
    @Id @GeneratedValue
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE_TYPE")
    private MemberRoleType roleType;
    private String name;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<Order>();

    public Member(Integer id){
        this.id = id;
    }
}