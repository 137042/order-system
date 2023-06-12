package kr.ac.kumoh.ordersystem;

import kr.ac.kumoh.ordersystem.dto.AddOrderMenuReq;
import kr.ac.kumoh.ordersystem.service.OrderService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@Transactional
@SpringBootTest
@WebAppConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class OrderBasketTest {

    @MockBean
    private Clock clock;

    @Autowired
    private OrderService orderService;

    @Test
    void justPrint(){
        System.out.println("test");
    }

    @Test
    void 운영시간외장바구니담기불가() {
        AddOrderMenuReq addOrderMenuReq = AddOrderMenuReq.builder()
                .memberId(1)
                .menuId(0)
                .menuId(1)
                .storeId(1)
                .orderPrice(1000)
                .potatoCount(1)
                .colaCount(1)
                .setCount(1)
                .singleCount(1)
                .build();

        given(clock.instant()).willReturn(Instant.parse("2021-06-01T01:00:00.00Z"));
        given(clock.getZone()).willReturn(ZoneOffset.UTC);

        assertThatThrownBy(() -> orderService.createOrAddMenu(addOrderMenuReq))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("영업시간이 아닙니다.");
    }

    @Test
    void 주메뉴()
    {

    }
}
