package kr.ac.kumoh.ordersystem.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.kumoh.ordersystem.domain.*;
import kr.ac.kumoh.ordersystem.dto.OrderReq;
import kr.ac.kumoh.ordersystem.mapper.OrderMapper;
import kr.ac.kumoh.ordersystem.mapper.OrderMenuMapper;
import kr.ac.kumoh.ordersystem.repository.OrderMenuRepository;
import kr.ac.kumoh.ordersystem.repository.OrderRepository;
import kr.ac.kumoh.ordersystem.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderWebSocketListHandler {

    private final ObjectMapper objectMapper;

    private final List<StoreWebSocketSession> storeSessionList;
    private final List<ClientWebSocketSession> clientSessionList;
    private final List<OrderThread> threadList;

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;
    private final OrderMenuMapper orderMenuMapper;
    private final OrderMenuRepository orderMenuRepository;

    private final StoreRepository storeRepository;

    public StoreWebSocketSession getStoreSession(Integer storeId){
        return storeSessionList.stream()
                .filter(s -> s.getStoreId().equals(storeId)).findAny()
                .orElseThrow(NoSuchElementException::new);
    }

    public List<StoreWebSocketSession> getStoreList(){
        return storeSessionList;
    }

    public void makeNewStore(WebSocketSession session, Integer storeId){
        storeSessionList.add(new StoreWebSocketSession(storeId, session));
    }

    public Order makeNewOrder(WebSocketSession session, OrderReq orderReq) throws IOException {
        Store store = storeRepository.findById(orderReq.getStoreId()).orElseThrow(NoSuchElementException::new);
        Order order = orderRepository.findById(orderReq.getOrderId()).orElseThrow(NoSuchElementException::new);
        order.setOrderTime();
        order.calculatePrice(orderMenuRepository);

        Optional<StoreWebSocketSession> possibleStoreSession = storeSessionList.stream()
                .filter(s -> s.getStoreId().equals(orderReq.getStoreId()))
                .findAny();
        StoreWebSocketSession storeSession;
        if(possibleStoreSession.isPresent())
            storeSession = possibleStoreSession.get();
        else {
            order.setStatus(OrderStatus.REJECTED);
            order = orderRepository.save(order);
            TextMessage textMessage = new TextMessage(objectMapper.writeValueAsString(orderMapper.toOrderRes(order)));
            session.sendMessage(textMessage);
            log.info("[ MESSAGE SENT ]  #{} {}", textMessage, "------------------------------------------------------------");
            return order;
        }

        if(store.getOpenTime().isAfter(LocalTime.now()) || store.getCloseTime().isBefore(LocalTime.now())) {
            order.setStatus(OrderStatus.REJECTED);
            order = orderRepository.save(order);
            storeSession.sendToStore(objectMapper, orderMapper.toOrderRes(order));

            TextMessage textMessage = new TextMessage(objectMapper.writeValueAsString(orderMapper.toOrderRes(order)));
            session.sendMessage(textMessage);
            log.info("[ MESSAGE SENT ] #{} {}", textMessage, "------------------------------------------------------------");
            return order;
        }

        order.setStatus(OrderStatus.ORDERED);
        order = orderRepository.save(order);
        storeSession.sendToStore(objectMapper, orderMapper.toOrderRes(order, orderMenuMapper.toOrderMenuRes(order.getOrderMenus())));
        clientSessionList.add(new ClientWebSocketSession(order, session));

        return order;
    }

    public void makeOrderThreadWaiting(Order order){
        log.info("ORDER ACCEPTANCE WAITING");
        OrderThread orderThread = new OrderThread(order, new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(60 * 1000);
                    order.setStatus(OrderStatus.REJECTED);
                    log.info("ORDER REJECTED");
                    updateOrderStatus(order);
                    notifyToStore(order);
                } catch (InterruptedException e) {
                    log.info("ORDER ACCEPTED");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }));
        threadList.add(orderThread);
    }

    public Order makeOrderOnDelivery(OrderReq orderReq) throws IOException {
        return updateOrderStatus(orderReq);
    }

    public void makeOrderThreadInterrupted(Order order){
        Thread thread = threadList.stream()
                .filter(t -> t.getOrder().getId().equals(order.getId())).findAny()
                .map(OrderThread::getThread)
                .orElseThrow(NoSuchElementException::new);
        thread.interrupt();
    }

    public void makeOrderRejected(OrderReq orderReq) throws IOException {
        updateOrderStatus(orderReq);
    }

    public void makeOrderDelivered(OrderReq orderReq) throws IOException {
        updateOrderStatus(orderReq);
    }

    public void removeFromStoreList(WebSocketSession session){
        storeSessionList.stream()
                .filter(s -> s.getWebSocketSession().getId().equals(session.getId()))
                .findAny().ifPresent(storeSessionList::remove);
        clientSessionList.stream()
                .filter(s -> s.getWebSocketSession().getId().equals(session.getId()))
                .findAny().ifPresent(clientSessionList::remove);
    }

    private Order updateOrderStatus(OrderReq orderReq) throws IOException {
        return updateOrderStatus(orderMapper.toOrder(orderReq));
    }

    private Order updateOrderStatus(Order order) throws IOException {
        ClientWebSocketSession clientWebSocketSession = clientSessionList.stream()
                .filter(c -> c.getOrder().getId().equals(order.getId()))
                .findAny().orElseThrow(NoSuchElementException::new);

        clientWebSocketSession.getOrder().setStatus(order.getStatus());
        orderRepository.save(clientWebSocketSession.getOrder());
        clientWebSocketSession.sendToClient(objectMapper, orderMapper.toOrderRes(clientWebSocketSession.getOrder()));

//        if(order.getStatus().equals(OrderStatus.DELIVERED) || order.getStatus().equals(OrderStatus.REJECTED))
//            clientWebSocketSession.getWebSocketSession().close();

        return order;
    }

    private void notifyToStore(Order order) throws IOException {
        StoreWebSocketSession storeSession = storeSessionList.stream()
                .filter(s -> s.getStoreId().equals(order.getStore().getId()))
                .findAny().orElseThrow(NoSuchElementException::new);

        storeSession.sendToStore(objectMapper, orderMapper.toOrderRes(order));
    }

}
