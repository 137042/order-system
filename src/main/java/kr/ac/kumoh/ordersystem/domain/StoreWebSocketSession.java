package kr.ac.kumoh.ordersystem.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Slf4j
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreWebSocketSession {

    private Integer storeId;
    private WebSocketSession webSocketSession;

    public void sendToStore(ObjectMapper objectMapper, Object object) throws IOException {
        CharSequence charSequence = objectMapper.writeValueAsString(object);
        webSocketSession.sendMessage(new TextMessage(charSequence));
        log.info("[ MESSAGE SENT ] #{} {}", charSequence, "------------------------------------------------------------");
    }

}
