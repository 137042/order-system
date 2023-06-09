package kr.ac.kumoh.ordersystem.controller;

import kr.ac.kumoh.ordersystem.domain.Order;
import kr.ac.kumoh.ordersystem.dto.AddOrderMenuReq;
import kr.ac.kumoh.ordersystem.dto.OrderMenuCountRes;
import kr.ac.kumoh.ordersystem.dto.OrderRes;
import kr.ac.kumoh.ordersystem.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/store/menu/count")
    public ResponseEntity<Map<String, Object>> getEachMenuCount(
    ){
        List<OrderMenuCountRes> orderMenuCountRes = orderService.findEachMenuCount();
        Map<String, Object> map = new HashMap<>();
        map.put("data", orderMenuCountRes);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Map<String, Object>> addOrderMenu(
            @RequestBody AddOrderMenuReq addOrderMenuAtOrderReq
            ){
        OrderRes orderRes = orderService.createOrAddMenu(addOrderMenuAtOrderReq);
        Map<String, Object> map = new HashMap<>();
        map.put("data", orderRes);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
