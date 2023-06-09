package kr.ac.kumoh.ordersystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import kr.ac.kumoh.ordersystem.domain.MemberRoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberRes {

    private Integer id;
    private String name;
    private MemberRoleType role;
    private String email;
    private String password;
}
