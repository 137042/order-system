package kr.ac.kumoh.ordersystem.service;

import kr.ac.kumoh.ordersystem.domain.Menu;
import kr.ac.kumoh.ordersystem.domain.Order;
import kr.ac.kumoh.ordersystem.domain.OrderStatus;
import kr.ac.kumoh.ordersystem.dto.*;
import kr.ac.kumoh.ordersystem.mapper.OrderCancelMapper;
import kr.ac.kumoh.ordersystem.mapper.OrderMapper;
import kr.ac.kumoh.ordersystem.mapper.OrderMenuMapper;
import kr.ac.kumoh.ordersystem.repository.MenuRepository;
import kr.ac.kumoh.ordersystem.repository.OrderMenuRepository;
import kr.ac.kumoh.ordersystem.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
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
    private final OrderCancelMapper orderCancelMapper;

    public List<OrderMenuCountRes> findEachMenuCount(){
        List<Menu> menuList = menuRepository.findAll();

        List<OrderMenuCountRes> orderMenuCountResList = new ArrayList<>();

        for (Menu menu : menuList) {
            try {
//                Integer count = orderMenuRepository.findByName(menu.getName());
//                orderMenuCountResList.add(orderMenuMapper.toOrderMenuCountRes(menu, count));
            } catch (NullPointerException e) {
                orderMenuCountResList.add(orderMenuMapper.toOrderMenuCountRes(menu, 0));
            }

        }
        return orderMenuCountResList;
    }
    public OrderRes createOrAddMenu(AddOrderMenuReq addOrderMenuReq){
        // SELECT o FROM Order o WHERE o.status = 'basket'
        List<Order> orderRepositoryBasket = orderRepository.findBasket();
        Order basketOrder;
        if(orderRepositoryBasket.isEmpty())
        {
            basketOrder = orderRepository.save(new Order());
            basketOrder.setStatus(OrderStatus.BASKET);
        }
        else
        {
            basketOrder = orderRepositoryBasket.get(0);
        }
        basketOrder.addOrderMenu(orderMenuMapper.toOrderMenu(basketOrder, addOrderMenuReq));
        return orderMapper.toOrderRes(basketOrder);
    }

    public OrderCancelRes cancelOrder(OrderReq orderReq)
    {
        Order order = orderRepository.findById(orderReq.getOrderId()).get();
        boolean success;
        if(order.getStatus() != OrderStatus.ORDERED){
            success = false;
        }
        else {
            success = true;
            order.setStatus(OrderStatus.REJECTED);
        }
        return orderCancelMapper.toOrderCancleRes(order, success);
    }
}
