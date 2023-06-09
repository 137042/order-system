package kr.ac.kumoh.ordersystem.controller;

import kr.ac.kumoh.ordersystem.dto.OrderMenuCountRes;
import kr.ac.kumoh.ordersystem.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/menu/count")
    public ResponseEntity<Map<String, Object>> getEachMenuCount(
    ){
        List<OrderMenuCountRes> orderMenuCountRes = orderService.findEachMenuCount();
        Map<String, Object> map = new HashMap<>();
        map.put("data", orderMenuCountRes);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
