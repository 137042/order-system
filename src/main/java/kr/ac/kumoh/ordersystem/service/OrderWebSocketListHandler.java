package kr.ac.kumoh.ordersystem.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.kumoh.ordersystem.domain.StoreWebSocketSession;
import kr.ac.kumoh.ordersystem.dto.OrderReq;
import kr.ac.kumoh.ordersystem.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class OrderWebSocketListHandler {

    private final List<StoreWebSocketSession> storeSessionList;
    private final OrderRepository orderRepository;

    public void addToStoreList(WebSocketSession session, int storeId){
        storeSessionList.add(new StoreWebSocketSession(storeId, session));
    }

    public void sendToStore(ObjectMapper objectMapper, WebSocketSession session, OrderReq orderReq) throws IOException {
        StoreWebSocketSession storeSession = storeSessionList.stream()
                .filter(s -> s.getStoreId() == orderReq.getStoreId())
                .findAny().orElseThrow(NoSuchElementException::new);
        sendToStore(objectMapper, storeSession, orderReq);
    }

    public void removeFromStoreList(WebSocketSession session){
        storeSessionList.stream()
                .filter(s -> s.getWebSocketSession().getId().equals(session.getId()))
                .findAny().ifPresent(storeSessionList::remove);
    }

    private void sendToStore(ObjectMapper objectMapper, StoreWebSocketSession storeSession, OrderReq orderReq) throws IOException {
        storeSession.getWebSocketSession().sendMessage(
                new TextMessage(objectMapper.writeValueAsString(orderReq)));
    }

}
