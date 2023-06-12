package kr.ac.kumoh.ordersystem;


import kr.ac.kumoh.ordersystem.domain.Order;
import kr.ac.kumoh.ordersystem.domain.OrderStatus;
import kr.ac.kumoh.ordersystem.dto.OrderCancelReq;
import kr.ac.kumoh.ordersystem.dto.OrderReq;
import kr.ac.kumoh.ordersystem.service.OrderService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@WebAppConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class OrderCustomer {

    @Autowired
    private OrderService orderService;

    @Test
    void 배달중주문취소불가(){
        OrderCancelReq order = OrderCancelReq.builder()
                .orderId(9)
                .status(OrderStatus.DELIVERED)
                .build();
        assertThat(orderService.cancelOrder(order).isCancelSuccess()).isEqualTo(false);
    }
}
