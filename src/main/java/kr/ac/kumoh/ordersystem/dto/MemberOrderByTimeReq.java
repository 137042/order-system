package kr.ac.kumoh.ordersystem.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberOrderByTimeReq {
    @NotNull
    private Integer memberId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
