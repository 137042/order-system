package kr.ac.kumoh.ordersystem.service;

import kr.ac.kumoh.ordersystem.domain.*;
import kr.ac.kumoh.ordersystem.dto.MemberOrderByTimeReq;
import kr.ac.kumoh.ordersystem.dto.MemberReq;
import kr.ac.kumoh.ordersystem.dto.MemberRes;
import kr.ac.kumoh.ordersystem.dto.OrderRes;
import kr.ac.kumoh.ordersystem.mapper.MemberMapper;
import kr.ac.kumoh.ordersystem.mapper.OrderMapper;
import kr.ac.kumoh.ordersystem.repository.MemberRepository;
import kr.ac.kumoh.ordersystem.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberMapper memberMapper;
    private final MemberRepository memberRepository;
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;

    public MemberRes findMember(String email){
        Member member = memberRepository.findByEmail(email);
        return memberMapper.toDto(member);
    }

    public List<OrderRes> findAllOrders(MemberReq memberReq) {
        List<Order> orderList = orderRepository.findAllByXorStatusAndMember(OrderStatus.BASKET, memberReq.getId());
//        // 장바구니용 order 제거
//        for (Order order : orderList)
//        {
//            if (order.getStatus() == OrderStatus.BASKET)
//                orderList.remove(order);
//        }
        List<OrderRes> orderResList = new ArrayList<>();
        for (Order order : orderList)
        {
            orderResList.add(orderMapper.toOrderResWithOrderMenu(order));
        }
        return orderResList;
    }

    public List<OrderRes> findOrdersBetweenTime(MemberOrderByTimeReq memberOrderByTimeReq) {
        List<Order> orderList = orderRepository.findOrderByTimeBetween(
                memberOrderByTimeReq.getMemberId(),
                removeTime(memberOrderByTimeReq.getStartDate()),
                removeTime(memberOrderByTimeReq.getEndDate()).plusDays(1)
        );

        List<OrderRes> orderResList = new ArrayList<>();
        for (Order order : orderList)
        {
            orderResList.add(orderMapper.toOrderResWithOrderMenu(order));
        }

        return orderResList;
    }
    private LocalDateTime removeTime(LocalDateTime localDateTime)
    {
        return localDateTime.withHour(0).withMinute(0).withSecond(0).withNano(0);
    }
}
