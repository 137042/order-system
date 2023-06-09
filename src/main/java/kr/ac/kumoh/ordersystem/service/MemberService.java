package kr.ac.kumoh.ordersystem.service;

import kr.ac.kumoh.ordersystem.domain.Member;
import kr.ac.kumoh.ordersystem.domain.Menu;
import kr.ac.kumoh.ordersystem.domain.MenuType;
import kr.ac.kumoh.ordersystem.domain.Order;
import kr.ac.kumoh.ordersystem.dto.MemberReq;
import kr.ac.kumoh.ordersystem.dto.MemberRes;
import kr.ac.kumoh.ordersystem.dto.MenuRes;
import kr.ac.kumoh.ordersystem.dto.OrderRes;
import kr.ac.kumoh.ordersystem.mapper.MemberMapper;
import kr.ac.kumoh.ordersystem.mapper.OrderMapper;
import kr.ac.kumoh.ordersystem.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberMapper memberMapper;
    private final MemberRepository memberRepository;
    private final OrderMapper orderMapper;

    public MemberRes findMember(String email){
        Member member = memberRepository.findByEmail(email);
        return memberMapper.toDto(member);
    }

    public List<OrderRes> findAllOrders(MemberReq memberReq) {
        List<Order> orderList = memberRepository.findByEmail(memberReq.getEmail()).getOrders();
        List<OrderRes> orderResList = new ArrayList<>();
        for (Order order : orderList)
        {
            orderResList.add(orderMapper.toOrderRes(order));
        }
        return orderResList;
    }
}
