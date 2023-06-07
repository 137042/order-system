package kr.ac.kumoh.ordersystem.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.kumoh.ordersystem.dto.OrderReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreWebSocketSession {

    private int storeId;
    private WebSocketSession webSocketSession;

    public void sendToStore(ObjectMapper objectMapper, OrderReq orderReq) throws IOException {
        webSocketSession.sendMessage(
                new TextMessage(objectMapper.writeValueAsString(orderReq)));
    }

}
