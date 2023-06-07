package kr.ac.kumoh.ordersystem.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.kumoh.ordersystem.domain.*;
import kr.ac.kumoh.ordersystem.dto.OrderReq;
import kr.ac.kumoh.ordersystem.mapper.OrderMapper;
import kr.ac.kumoh.ordersystem.mapper.OrderMenuMapper;
import kr.ac.kumoh.ordersystem.repository.OrderMenuRepository;
import kr.ac.kumoh.ordersystem.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@RequiredArgsConstructor
public class OrderWebSocketListHandler {

    private final ObjectMapper objectMapper;
    private final List<StoreWebSocketSession> storeSessionList;
    private List<OrderThread> threadList = new CopyOnWriteArrayList<>();

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    private final OrderMenuRepository orderMenuRepository;
    private final OrderMenuMapper orderMenuMapper;

    public void addToStoreList(WebSocketSession session, Integer storeId){
        storeSessionList.add(new StoreWebSocketSession(storeId, session));
    }

    public void sendToStore(WebSocketSession session, OrderReq orderReq) throws IOException {
        Order order = saveOrderAndOrderMenu(orderReq);
        StoreWebSocketSession storeSession = storeSessionList.stream()
                .filter(s -> s.getStoreId().equals(orderReq.getStoreId()))
                .findAny().orElseThrow(NoSuchElementException::new);
        storeSession.sendToStore(objectMapper, orderReq);
        handleOrder(storeSession, session, orderReq, order);
    }

    public void removeFromStoreList(WebSocketSession session){
        storeSessionList.stream()
                .filter(s -> s.getWebSocketSession().getId().equals(session.getId()))
                .findAny().ifPresent(storeSessionList::remove);
    }

    public void acceptOrder(WebSocketSession session, OrderReq orderReq){
        OrderThread orderThread = threadList.stream()
                .filter(t -> t.getOrderId().equals(orderReq.getOrderId()))
                .findAny().orElseThrow(NoSuchElementException::new);
        orderThread.interrupt();
    }

    private Order saveOrderAndOrderMenu(OrderReq orderReq){
        Order order = orderRepository.save(orderMapper.toOrder(orderReq));
        orderMenuRepository.saveAll(orderMenuMapper.toOrderMenu(order, orderReq.getOrderMenuReqList()));
        return order;
    }

    private void handleOrder(StoreWebSocketSession storeSession, WebSocketSession session, OrderReq orderReq, Order order) throws IOException {
        StoreThread storeThread = new StoreThread(order, Thread.currentThread());
        storeThread.handleThread(orderMapper, orderRepository, storeSession, objectMapper, session);
//        OrderThread orderThread = new OrderThread(order);
//        synchronized (orderThread){
//            try {
//                threadList.add(orderThread);
//                orderThread.wait(1000);
//                order.setStatus(OrderStatus.REJECTED);
//                orderRepository.save(order);
//                storeSession.sendToStore(objectMapper, replyToClient(session, order));
//            } catch (InterruptedException e){
//                order.setStatus(OrderStatus.ON_DELIVERY);
//                orderRepository.save(order);
//                storeSession.sendToStore(objectMapper, replyToClient(session, order));
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
    }

    private TextMessage replyToClient(WebSocketSession session, Order order) throws IOException {
        TextMessage textMessage = new TextMessage(objectMapper.writeValueAsString(orderMapper.toOrderRes(order)));
        session.sendMessage(textMessage);
        return textMessage;
    }

}
