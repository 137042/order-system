package kr.ac.kumoh.ordersystem.service;

import kr.ac.kumoh.ordersystem.domain.*;
import kr.ac.kumoh.ordersystem.dto.*;
import kr.ac.kumoh.ordersystem.mapper.BasketMenuMapper;
import kr.ac.kumoh.ordersystem.mapper.OrderCancelMapper;
import kr.ac.kumoh.ordersystem.mapper.OrderMapper;
import kr.ac.kumoh.ordersystem.mapper.OrderMenuMapper;
import kr.ac.kumoh.ordersystem.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final BasketMenuMapper basketMenuMapper;

    public List<OrderMenuCountRes> findEachMenuCount(){
        List<Menu> menuList = menuRepository.findAllMain(MenuType.Main);

        List<OrderMenuCountRes> orderMenuCountResList = new ArrayList<>();

//        int potatoCount = 0;
//        int colaCount = 0;

        for (Menu menu : menuList) {
            try {
                int count = 0;
                int setCount = 0;

                List<OrderMenu> orderMenuList = orderMenuRepository.findAllById(menu.getId());
                for (OrderMenu orderMenu : orderMenuList) {
                    count += orderMenu.getSingleCount();
                    setCount += orderMenu.getSetCount();
//                    potatoCount += orderMenu.getPotatoCount();
//                    colaCount += orderMenu.getColaCount();
                }
                orderMenuCountResList.add(orderMenuMapper.toOrderMenuCountRes(menu, count, setCount));
            } catch (NullPointerException e) {
                orderMenuCountResList.add(orderMenuMapper.toOrderMenuCountRes(menu, 0, 0));
            }
        }

//        Menu cola = menuRepository.findByName("콜라");
//        orderMenuCountResList.add(orderMenuMapper.toOrderMenuCountRes(cola, colaCount, 0));
//        Menu potato = menuRepository.findByName("감자튀김");
//        orderMenuCountResList.add(orderMenuMapper.toOrderMenuCountRes(potato, potatoCount, 0));

        return orderMenuCountResList;
    }
    public OrderRes createOrAddMenu(AddOrderMenuReq addOrderMenuReq){
        Order basketOrder = getBasket(addOrderMenuReq);
        basketOrder.addOrderMenu(orderMenuMapper.toOrderMenu(basketOrder, addOrderMenuReq));
        return orderMapper.toOrderResWithOrderMenu(basketOrder);
    }

    public OrderCancelRes cancelOrder(OrderCancelReq orderCancelReq)
    {
        Order order = orderRepository.findById(orderCancelReq.getOrderId()).get();
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

    public OrderRes getBasketRes(MemberReq memberReq) {
        return orderMapper.toOrderResWithOrderMenu(getBasket(memberReq));
    }

    public List<OrderRes> getStoreOrders(Integer id){
        List<Order> orderList = orderRepository.findAllByStore(id, OrderStatus.BASKET);
        List<OrderRes> orderResList = new ArrayList<>();
        for (Order order : orderList)
        {
            orderResList.add(orderMapper.toOrderRes(order, orderMenuMapper.toOrderMenuRes(order.getOrderMenus())));
        }
        return orderResList;
    }

    public Optional<OrderRes> getBasketMenuResList(MemberReq memberReq){
        Order basket = getBasket(memberReq);
//        List<BasketMenuRes> basketMenuRes = basketMenuMapper.toBasketMenuResList(basket, basket.getOrderMenus());
//        return basketMenuRes;
        if(basket == null)
            return Optional.empty();
        return Optional.of(orderMapper.toOrderRes(basket, orderMenuMapper.toOrderMenuRes(basket.getOrderMenus())));
    }

    private Order getBasket(MemberReq memberReq)
    {
        Member member = memberRepository.findById(memberReq.getId()).get();
        return getBasket(member);
    }
    private Order getBasket(AddOrderMenuReq addOrderMenuReq)
    {
        Member member = memberRepository.findById(addOrderMenuReq.getMemberId()).get();
        Store store = storeRepository.findById(addOrderMenuReq.getStoreId()).get();
        return getBasket(member, store);
    }
    private Order getBasket(Member member)
    {
        List<Order> orderRepositoryBasket = orderRepository.findBasket(member);
        if(orderRepositoryBasket.isEmpty())
            return null;
        Store store = orderRepositoryBasket.get(0).getStore();
        return getBasket(member, store);
    }
    private Order getBasket(Member member, Store store){
        // SELECT o FROM Order o WHERE o.status = 'basket'
        List<Order> orderRepositoryBasket = orderRepository.findBasket(member);
        Order basketOrder;
        if(orderRepositoryBasket.isEmpty() || orderRepositoryBasket.get(0).getStore() != store)
        {
            for (Order order : orderRepositoryBasket)
            {
                orderRepository.delete(order);
            }

            basketOrder = orderRepository.save(
                    Order.builder()
                            .member(member)
                            .status(OrderStatus.BASKET)
                            .store(store)
                            .build()
                    );
        }
        else
        {
            basketOrder = orderRepositoryBasket.get(0);
        }
        return basketOrder;
    }
}
