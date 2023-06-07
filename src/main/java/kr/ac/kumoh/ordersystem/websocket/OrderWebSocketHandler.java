package kr.ac.kumoh.ordersystem.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.kumoh.ordersystem.domain.OrderStatus;
import kr.ac.kumoh.ordersystem.dto.OrderReq;
import kr.ac.kumoh.ordersystem.service.OrderMenuService;
import kr.ac.kumoh.ordersystem.service.OrderWebSocketListHandler;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderWebSocketHandler extends AbstractWebSocketHandler {

    private final String hr = "--------------------------------------------------";
    private final ObjectMapper objectMapper;

    private final OrderWebSocketListHandler orderWebSocketListHandler;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("[ SESSION {} ] CONNECTED {}", session.getId(), hr);
    }

    @Override
    public void handleMessage(@NonNull WebSocketSession session, @NonNull WebSocketMessage<?> webSocketMessage) throws Exception {
        log.info("[ SESSION {} ] RAW MESSAGE RECEIVED {}", session.getId(), hr);
        if(webSocketMessage instanceof TextMessage)
            handleTextMessage(session, (TextMessage) webSocketMessage);
        else
            throw new IllegalStateException();
    }

    @Override
    public void handleTextMessage(@NonNull WebSocketSession session, TextMessage textMessage) throws IOException {
        OrderReq orderReq = objectMapper.readValue(textMessage.getPayload(), OrderReq.class);
        if(orderReq.getMemberId() == 0 && orderReq.getOrderMenuReqList() == null)
            orderWebSocketListHandler.addToStoreList(session, orderReq.getStoreId());
        else if(orderReq.getStatus().equals(OrderStatus.ORDERED))
            orderWebSocketListHandler.sendToStore(objectMapper, session, orderReq);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status){
        orderWebSocketListHandler.removeFromStoreList(session);
    }

}
