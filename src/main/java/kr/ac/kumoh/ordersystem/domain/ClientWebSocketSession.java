package kr.ac.kumoh.ordersystem.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
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
public class ClientWebSocketSession {

    private Order order;
    private WebSocketSession webSocketSession;

    public void sendToClient(ObjectMapper objectMapper, Object object) throws IOException {
        webSocketSession.sendMessage(
                new TextMessage(objectMapper.writeValueAsString(object)));
    }

}
