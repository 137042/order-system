package kr.ac.kumoh.ordersystem.controller;

import com.sun.jdi.request.DuplicateRequestException;
import kr.ac.kumoh.ordersystem.domain.DiscountType;
import kr.ac.kumoh.ordersystem.dto.MenuCreateReq;
import kr.ac.kumoh.ordersystem.dto.MenuReq;
import kr.ac.kumoh.ordersystem.dto.MenuRes;
import kr.ac.kumoh.ordersystem.dto.OrderMenuCountRes;
import kr.ac.kumoh.ordersystem.service.MenuService;
import kr.ac.kumoh.ordersystem.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService;

    private final OrderService orderService;

    @PostMapping("/list")
    public ResponseEntity<Map<String, Object>> getAllMenus(){
        List<MenuRes> allMenus = menuService.findAllMenus();
        Map<String, Object> map = new HashMap<>();
        map.put("data", allMenus);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/main/list")
    public ResponseEntity<Map<String, Object>> getMainMenus(){
        List<MenuRes> allMenus = menuService.findAllMain();
        Map<String, Object> map = new HashMap<>();
        map.put("data", allMenus);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getMenu(
            @PathVariable("id") Integer menuId
    ){
        MenuRes menu = menuService.findOne(menuId);
        Map<String, Object> map = new HashMap<>();
        map.put("data", menu);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/count")
    public ResponseEntity<Map<String, Object>> getMainCount(){
        int res = menuService.findAllMainCount();
        Map<String, Object> map = new HashMap<>();
        map.put("data", res);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> postMenu(
            @RequestBody @Valid MenuCreateReq menuReq
    ){
        try {
            MenuRes menu = menuService.createMenu(menuReq);
            Map<String, Object> map = new HashMap<>();
            map.put("data", menu);
            return new ResponseEntity<>(map, HttpStatus.CREATED);

        } catch (DuplicateRequestException e) {
            Map<String, Object> map = new HashMap<>();
            map.put("data", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<Map<String, Object>> putMenu(
            @RequestBody @Valid MenuReq menuReq
    ){
        Boolean isSuccess = menuService.updateMenu(menuReq);
        Map<String, Object> map = new HashMap<>();
        map.put("data", isSuccess);
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @PutMapping("/updateDiscount/{id}/{discount}")
    public ResponseEntity<Map<String, Object>> putMenuDiscount(
            @PathVariable("id") Integer menuId,
            @PathVariable("discount")  String discountPolicy
    ){
        Boolean isSuccess = menuService.updateDiscountPolicy(menuId, DiscountType.valueOf(discountPolicy));
        Map<String, Object> map = new HashMap<>();
        map.put("data", isSuccess);
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }
}
