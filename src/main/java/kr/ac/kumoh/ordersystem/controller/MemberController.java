package kr.ac.kumoh.ordersystem.controller;

import kr.ac.kumoh.ordersystem.domain.Member;
import kr.ac.kumoh.ordersystem.domain.MemberRoleType;
import kr.ac.kumoh.ordersystem.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
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

}