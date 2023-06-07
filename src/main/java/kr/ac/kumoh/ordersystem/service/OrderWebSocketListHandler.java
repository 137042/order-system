package kr.ac.kumoh.ordersystem.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.kumoh.ordersystem.domain.*;
import kr.ac.kumoh.ordersystem.dto.OrderMenuRes;
import kr.ac.kumoh.ordersystem.dto.OrderReq;
import kr.ac.kumoh.ordersystem.mapper.OrderMapper;
import kr.ac.kumoh.ordersystem.mapper.OrderMenuMapper;
import kr.ac.kumoh.ordersystem.repository.OrderMenuRepository;
import kr.ac.kumoh.ordersystem.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class OrderWebSocketListHandler {

    private final ObjectMapper objectMapper;
    private final List<StoreWebSocketSession> storeSessionList;
    private final List<ClientWebSocketSession> clientSessionList;

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    private final OrderMenuRepository orderMenuRepository;
    private final OrderMenuMapper orderMenuMapper;

    public void addToStoreList(WebSocketSession session, Integer storeId){
        storeSessionList.add(new StoreWebSocketSession(storeId, session));
    }

    public void sendOrderToStore(WebSocketSession session, OrderReq orderReq) throws IOException{
        Order order = orderRepository.save(orderMapper.toOrder(orderReq));
        List<OrderMenu> orderMenuList = orderMenuRepository.saveAll(orderMenuMapper.toOrderMenu(order, orderReq.getOrderMenuReqList()));

        StoreWebSocketSession storeSession = storeSessionList.stream()
                .filter(s -> s.getStoreId().equals(orderReq.getStoreId()))
                .findAny().orElseThrow(NoSuchElementException::new);

        List<OrderMenuRes> orderMenuResList = orderMenuMapper.toOrderMenuRes(orderMenuList);
        storeSession.sendToStore(objectMapper, orderMapper.toOrderRes(order, orderMenuResList));
        clientSessionList.add(new ClientWebSocketSession(order, session));
    }

    public void sendChangedOrderStatusToClient(OrderReq orderReq) throws IOException {
        ClientWebSocketSession clientWebSocketSession = clientSessionList.stream()
                .filter(c -> c.getOrder().getId().equals(orderReq.getOrderId()))
                .findAny().orElseThrow(NoSuchElementException::new);
        clientWebSocketSession.getOrder().setStatus(orderReq.getStatus());
        orderRepository.save(clientWebSocketSession.getOrder());
        clientWebSocketSession.sendToClient(objectMapper, orderMapper.toOrderRes(clientWebSocketSession.getOrder()));
        if(orderReq.getStatus().equals(OrderStatus.DELIVERED) || orderReq.getStatus().equals(OrderStatus.REJECTED))
            clientWebSocketSession.getWebSocketSession().close();
    }

    public void removeFromStoreList(WebSocketSession session){
        storeSessionList.stream()
                .filter(s -> s.getWebSocketSession().getId().equals(session.getId()))
                .findAny().ifPresent(storeSessionList::remove);
        clientSessionList.stream()
                .filter(s -> s.getWebSocketSession().getId().equals(session.getId()))
                .findAny().ifPresent(clientSessionList::remove);
    }

}
