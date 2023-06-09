package kr.ac.kumoh.ordersystem.service;

import kr.ac.kumoh.ordersystem.domain.Menu;
import kr.ac.kumoh.ordersystem.domain.Order;
import kr.ac.kumoh.ordersystem.dto.AddOrderMenuReq;
import kr.ac.kumoh.ordersystem.dto.OrderMenuCountRes;
import kr.ac.kumoh.ordersystem.dto.OrderRes;
import kr.ac.kumoh.ordersystem.mapper.OrderMapper;
import kr.ac.kumoh.ordersystem.mapper.OrderMenuMapper;
import kr.ac.kumoh.ordersystem.repository.MenuRepository;
import kr.ac.kumoh.ordersystem.repository.OrderMenuRepository;
import kr.ac.kumoh.ordersystem.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderMenuMapper orderMenuMapper;
    private final OrderMenuRepository orderMenuRepository;
    private final MenuRepository menuRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public List<OrderMenuCountRes> findEachMenuCount(){
        List<Menu> menuList = menuRepository.findAll();

        List<OrderMenuCountRes> orderMenuCountResList = new ArrayList<>();

        for (Menu menu : menuList) {
            try {
                Integer count = orderMenuRepository.findByName(menu.getName());
                orderMenuCountResList.add(orderMenuMapper.toOrderMenuCountRes(menu, count));
            } catch (NullPointerException e) {
                orderMenuCountResList.add(orderMenuMapper.toOrderMenuCountRes(menu, 0));
            }

        }
        return orderMenuCountResList;
    }
    public OrderRes createOrAddMenu(AddOrderMenuReq addOrderMenuReq){
        Order order;
        if(addOrderMenuReq.getOrderId() == null)
        {
            order = orderRepository.save(new Order());
        }
        else
        {
            order = orderRepository.findById(addOrderMenuReq.getOrderId()).get();
        }
        order.addOrderMenu(orderMenuMapper.toOrderMenu(order, addOrderMenuReq));
        return orderMapper.toOrderRes(order);
    }
}
