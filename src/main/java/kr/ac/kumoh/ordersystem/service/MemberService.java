package kr.ac.kumoh.ordersystem.service;

import kr.ac.kumoh.ordersystem.domain.Member;
import kr.ac.kumoh.ordersystem.dto.MemberRes;
import kr.ac.kumoh.ordersystem.mapper.MemberMapper;
import kr.ac.kumoh.ordersystem.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberMapper memberMapper;
    private final MemberRepository memberRepository;

    public MemberRes findMember(String email){
        Member member = memberRepository.findByEmail(email);
        return memberMapper.toDto(member);
    }

}
