package kr.ac.kumoh.ordersystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.kumoh.ordersystem.domain.OrderStatus;
import kr.ac.kumoh.ordersystem.dto.OrderReq;
import kr.ac.kumoh.ordersystem.service.OrderWebSocketListHandler;
import kr.ac.kumoh.ordersystem.websocket.OrderWebSocketHandler;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.adapter.standard.StandardWebSocketSession;

import static org.mockito.Mockito.mock;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class OrderWebSocketTest {

    @Autowired
    OrderWebSocketHandler orderWebSocketHandler;

    @Autowired
    OrderWebSocketListHandler orderWebSocketListHandler;

    @Autowired
    ObjectMapper objectMapper;

    WebSocketSession storeSession, clientSession;
    OrderReq reg, order, accept, reject, complete;
    WebSocketMessage<String> regMsg, orderMenu, acceptOrder, rejectOrder, completeOrder;
    final Integer ORDER_ID = 9, STORE_ID = 1, MEMBER_ID = 1;

    @Before
    public void setup() throws JsonProcessingException {
        MockitoAnnotations.openMocks(this);

        storeSession = mock(StandardWebSocketSession.class);
        clientSession = mock(StandardWebSocketSession.class);

        reg = new OrderReq(null, null, STORE_ID, null);
        order = new OrderReq(ORDER_ID, OrderStatus.ORDERED, STORE_ID, MEMBER_ID);
        accept = new OrderReq(ORDER_ID, OrderStatus.ON_DELIVERY, STORE_ID, null);
        reject = new OrderReq(ORDER_ID, OrderStatus.REJECTED, STORE_ID, null);
        complete = new OrderReq(ORDER_ID, OrderStatus.DELIVERED, STORE_ID, null);

        regMsg = new TextMessage(objectMapper.writeValueAsString(reg));
        orderMenu = new TextMessage(objectMapper.writeValueAsString(order));
        acceptOrder = new TextMessage(objectMapper.writeValueAsString(accept));
        rejectOrder = new TextMessage(objectMapper.writeValueAsString(reject));
        completeOrder = new TextMessage(objectMapper.writeValueAsString(complete));
    }

    @Test
    void storeReg() throws JsonProcessingException {
        setup();

        orderWebSocketHandler.handleMessage(storeSession, regMsg);
        System.out.println("등록한 가게 ID: " + reg.getStoreId()
                + "\n방금 등록된 점주 세션: " + orderWebSocketListHandler.getStoreSession(reg.getStoreId()));
    }

    @Test
    void orderSendAndAutoRejected() throws JsonProcessingException, InterruptedException {
        setup();

        orderWebSocketHandler.handleMessage(storeSession, regMsg);
        orderWebSocketHandler.handleMessage(clientSession, orderMenu);
        synchronized (this){
            wait(65 * 1000L);
        }
    }

    @Test
    void orderSendAndAccepted() throws JsonProcessingException, InterruptedException {
        setup();

        orderWebSocketHandler.handleMessage(storeSession, regMsg);
        orderWebSocketHandler.handleMessage(clientSession, orderMenu);

        synchronized (this) {
            wait(1000L);
        }
        orderWebSocketHandler.handleMessage(storeSession, acceptOrder);

        synchronized (this) {
            wait(1000L);
        }
        orderWebSocketHandler.handleMessage(storeSession, completeOrder);
    }

    @Test
    void orderSendAndReject() throws JsonProcessingException, InterruptedException {
        setup();

        orderWebSocketHandler.handleMessage(storeSession, regMsg);
        orderWebSocketHandler.handleMessage(clientSession, orderMenu);

        synchronized (this) {
            wait(1000L);
        }
        orderWebSocketHandler.handleMessage(storeSession, rejectOrder);
    }

}
