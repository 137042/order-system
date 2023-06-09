package kr.ac.kumoh.ordersystem;

import kr.ac.kumoh.ordersystem.domain.MenuType;
import kr.ac.kumoh.ordersystem.dto.MenuCreateReq;
import kr.ac.kumoh.ordersystem.dto.MenuReq;
import kr.ac.kumoh.ordersystem.dto.MenuRes;
import kr.ac.kumoh.ordersystem.dto.OrderMenuCountRes;
import kr.ac.kumoh.ordersystem.repository.MenuRepository;
import kr.ac.kumoh.ordersystem.service.MenuService;
import kr.ac.kumoh.ordersystem.service.OrderService;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class MenuTest {
    @Autowired
    MenuService menuService;
    @Autowired
    OrderService orderService;
    @Autowired
    MenuRepository menuRepository;
    @Test
    void createMenu() throws Exception {
        MenuCreateReq menuReq = new MenuCreateReq("치즈햄버거", 5000, "치즈가 많아요", "어어러다ㅣ", MenuType.Main);
        MenuRes menuRes = menuService.createMenu(menuReq);
        MenuRes findMenu = menuService.findOne(menuRes.getId());
        AssertionsForClassTypes.assertThat(menuRes)
                .as(() -> "생성 메뉴와 조회된 메뉴는 같아야 함")
                .isEqualToComparingFieldByFieldRecursively(findMenu);
        System.out.println(menuRes.toString());
        System.out.println(findMenu.toString());
    }

    @Test
    void createDuplicateMenu() throws Exception {
        MenuCreateReq menuReq1 = new MenuCreateReq("치즈햄버거", 5000, "치즈가 많아요", "dfff", MenuType.Main);
        MenuCreateReq menuReq2 = new MenuCreateReq("치즈햄버거", 6000, "치즈가 많아요", "fffff", MenuType.Main);
        MenuRes menuRes1 = menuService.createMenu(menuReq1);
        MenuRes menuRes2 = menuService.createMenu(menuReq2);

    }

    @Test
    void updateMenu() throws Exception {
        MenuCreateReq menuReq = new MenuCreateReq("치즈햄버거", 5000, "치즈가 많아요", "어어러다ㅣ", MenuType.Main);
        MenuRes menuRes = menuService.createMenu(menuReq);
        MenuRes findMenu = menuService.findOne(menuRes.getId());
        System.out.println("생성 메뉴 id : " + findMenu.getId() + " 이름 : " + findMenu.getName() + " 가격 : " + findMenu.getPrice() + " 설명 : " + findMenu.getDescription());

        MenuReq updateMenu = new MenuReq(menuRes.getId(), "치즈햄버거", 5000, "ㅁㅁㅁ", "123", MenuType.Main);
        Boolean isSuccess = menuService.updateMenu(updateMenu);
        MenuRes updateFindMenu = menuService.findOne(menuRes.getId());
        System.out.println("수정 메뉴 id : " + updateFindMenu.getId() + " 이름 : " + updateFindMenu.getName() + " 가격 : " + updateFindMenu.getPrice() + " 설명 : " + updateFindMenu.getDescription());
    }

    @Test
    void getMenuCount() throws Exception {
        List<OrderMenuCountRes> orderMenuCountResList = orderService.findEachMenuCount();
        for (OrderMenuCountRes order : orderMenuCountResList) {
            System.out.println(order.getName() + " : " + order.getCount());
        }
    }
}
