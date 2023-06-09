package kr.ac.kumoh.ordersystem.mapper;

import kr.ac.kumoh.ordersystem.domain.Member;
import kr.ac.kumoh.ordersystem.dto.MemberRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class MemberMapper {
    public MemberRes toDto(Member member) {
        MemberRes builder = MemberRes.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .password(member.getPassword())
                .role(member.getRole())
                .build();
        return builder;
    }

    public Member toEntity(Member req){
        Member.MemberBuilder builder = Member.builder();
        builder
                .id(req.getId())
                .name(req.getName())
                .email(req.getEmail())
                .password(req.getPassword())
                .provider("")
                .role(req.getRole());

        return builder.build();
    }
}
