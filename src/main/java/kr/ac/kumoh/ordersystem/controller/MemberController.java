package kr.ac.kumoh.ordersystem.controller;

import kr.ac.kumoh.ordersystem.dto.MemberOrderByTimeReq;
import kr.ac.kumoh.ordersystem.dto.MemberReq;
import kr.ac.kumoh.ordersystem.dto.OrderRes;
import kr.ac.kumoh.ordersystem.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberService memberService;



//    @PostMapping ("/login")
//    public ResponseEntity<Map<String, Object>> login(
//            @RequestBody @Valid MemberReq memberreq
//    ){
//        System.out.println(memberreq.getEmail());
//        MemberRes memberRes = memberService.findMember(memberreq.getEmail());
//        Map<String, Object> map = new HashMap<>();
//        map.put("data", memberRes);
//        return new ResponseEntity<>(map, HttpStatus.OK);
//    }

    @PostMapping("/member/orderList")
    public ResponseEntity<Map<String, Object>> getMemberOrders(
            @RequestBody MemberReq memberReq
            ){
        List<OrderRes> allOrders = memberService.findAllOrders(memberReq);
        Map<String, Object> map = new HashMap<>();
        map.put("data", allOrders);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/member/orderList/time")
    public ResponseEntity<Map<String, Object>> getMemberOrdersBetweenTime(
            @RequestBody MemberOrderByTimeReq memberOrderByTimeReq
            ){
        List<OrderRes> ordersByTime = memberService.findOrdersBetweenTime(memberOrderByTimeReq);
        Map<String, Object> map = new HashMap<>();
        map.put("data", ordersByTime);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}