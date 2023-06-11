package kr.ac.kumoh.ordersystem.ws_test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.kumoh.ordersystem.dto.OrderReq;
import kr.ac.kumoh.ordersystem.service.OrderWebSocketListHandler;
import kr.ac.kumoh.ordersystem.websocket.OrderWebSocketHandler;
import org.assertj.core.internal.bytebuddy.implementation.bind.annotation.RuntimeType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import static org.mockito.Mockito.mock;

//@Transactional
@SpringBootTest
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@ExtendWith(MockitoExtension.class)
public class StoreTest {

//    @Autowired
    @Mock
    OrderWebSocketHandler orderWebSocketHandler;

//    @Autowired
    @Mock
    OrderWebSocketListHandler orderWebSocketListHandler;

    @Autowired
//    @Mock
    ObjectMapper objectMapper;

    @Test
    void storeReg() throws JsonProcessingException {
        OrderReq orderReq = new OrderReq(null, null, 1, null);
        WebSocketSession storeSession = mock(WebSocketSession.class);
        WebSocketMessage<String> testMessage = new TextMessage(objectMapper.writeValueAsString(orderReq));
        orderWebSocketHandler.handleMessage(storeSession, testMessage);

        System.out.println(orderWebSocketListHandler.getStoreSession(orderReq.getStoreId()));
    }


//    @Test
//    void send() throws JsonProcessingException {
//        OrderReq orderReq = new OrderReq(1, OrderStatus.ORDERED, 1, 1);
//        WebSocketSession storeSession = mock(WebSocketSession.class);
//        WebSocketSession clientSession = mock(WebSocketSession.class);
//        WebSocketMessage testMessage = new TextMessage(objectMapper.writeValueAsString(orderReq));
//
//
//        orderWebSocketHandler.handleMessage();
//
//
//    }

}
