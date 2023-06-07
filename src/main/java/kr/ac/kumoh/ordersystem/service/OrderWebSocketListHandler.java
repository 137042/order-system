package kr.ac.kumoh.ordersystem.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.kumoh.ordersystem.domain.Order;
import kr.ac.kumoh.ordersystem.domain.StoreWebSocketSession;
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

    private final List<StoreWebSocketSession> storeSessionList;

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    private final OrderMenuRepository orderMenuRepository;
    private final OrderMenuMapper orderMenuMapper;

    public void addToStoreList(WebSocketSession session, int storeId){
        storeSessionList.add(new StoreWebSocketSession(storeId, session));
    }

    public void sendToStore(ObjectMapper objectMapper, WebSocketSession session, OrderReq orderReq) throws IOException {
        saveOrderAndOrderMenu(orderReq);
        StoreWebSocketSession storeSession = storeSessionList.stream()
                .filter(s -> s.getStoreId() == orderReq.getStoreId())
                .findAny().orElseThrow(NoSuchElementException::new);
        storeSession.sendToStore(objectMapper, orderReq);
    }

    public void removeFromStoreList(WebSocketSession session){
        storeSessionList.stream()
                .filter(s -> s.getWebSocketSession().getId().equals(session.getId()))
                .findAny().ifPresent(storeSessionList::remove);
    }

    private void saveOrderAndOrderMenu(OrderReq orderReq){
        Order order = orderRepository.save(orderMapper.toOrder(orderReq));
        orderMenuRepository.saveAll(orderMenuMapper.toOrderMenu(order, orderReq.getOrderMenuReqList()));
    }

}
