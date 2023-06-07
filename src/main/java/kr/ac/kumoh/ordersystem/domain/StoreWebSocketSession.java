package kr.ac.kumoh.ordersystem.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreWebSocketSession {

    private int storeId;
    private WebSocketSession webSocketSession;

}
