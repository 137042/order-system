package kr.ac.kumoh.ordersystem;

import kr.ac.kumoh.ordersystem.domain.DiscountType;
import kr.ac.kumoh.ordersystem.domain.MenuType;
import kr.ac.kumoh.ordersystem.dto.*;
import kr.ac.kumoh.ordersystem.repository.MenuRepository;
import kr.ac.kumoh.ordersystem.service.MenuService;
import kr.ac.kumoh.ordersystem.service.OrderService;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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
    void 메뉴생성() throws Exception {
        MenuCreateReq menuReq = new MenuCreateReq("치즈햄버거", 5000, "치즈가 많아요", "어어러다ㅣ", MenuType.Main);
        MenuRes menuRes = menuService.createMenu(menuReq);
        MenuRes findMenu = menuService.findOne(menuRes.getId());
        AssertionsForClassTypes.assertThat(menuRes)
                .as(() -> "생성 메뉴와 조회된 메뉴는 같아야 함")
                .isEqualToComparingFieldByFieldRecursively(findMenu);
        System.out.println("생성 메뉴 id : " + menuRes.getId() + " 이름 : " + menuRes.getName() + " 가격 : " + menuRes.getPrice() + " 설명 : " + menuRes.getDescription());
        System.out.println("조회 메뉴 id : " + findMenu.getId() + " 이름 : " + findMenu.getName() + " 가격 : " + findMenu.getPrice() + " 설명 : " + findMenu.getDescription());
    }

    @Test
    void 동일이름메뉴생성() throws Exception {
        MenuCreateReq menuReq1 = new MenuCreateReq("치즈햄버거", 5000, "치즈가 많아요", "dfff", MenuType.Main);
        MenuCreateReq menuReq2 = new MenuCreateReq("치즈햄버거", 6000, "치즈가 많아요", "fffff", MenuType.Main);
        MenuRes menuRes1 = menuService.createMenu(menuReq1);
        System.out.println("생성 메뉴 id : " + menuRes1.getId() + " 이름 : " + menuRes1.getName() + " 가격 : " + menuRes1.getPrice() + " 설명 : " + menuRes1.getDescription());
        MenuRes menuRes2 = menuService.createMenu(menuReq2);
        System.out.println("생성 메뉴 id : " + menuRes2.getId() + " 이름 : " + menuRes2.getName() + " 가격 : " + menuRes2.getPrice() + " 설명 : " + menuRes2.getDescription());

    }

    @Test
    void 동일이름메뉴수정() throws Exception {
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
    void 메뉴수정() throws Exception {
        MenuCreateReq menuReq = new MenuCreateReq("치즈햄버거", 5000, "치즈가 많아요", "어어러다ㅣ", MenuType.Main);
        MenuRes menuRes = menuService.createMenu(menuReq);
        MenuRes findMenu = menuService.findOne(menuRes.getId());
        System.out.println("생성 메뉴 id : " + findMenu.getId() + " 이름 : " + findMenu.getName() + " 가격 : " + findMenu.getPrice() + " 설명 : " + findMenu.getDescription());

        MenuReq updateMenu = new MenuReq(menuRes.getId(), "불고기햄버거", 6000, "불고기 버거 입니다.", "123", MenuType.Main);
        Boolean isSuccess = menuService.updateMenu(updateMenu);
        MenuRes updateFindMenu = menuService.findOne(menuRes.getId());
        System.out.println("수정 메뉴 id : " + updateFindMenu.getId() + " 이름 : " + updateFindMenu.getName() + " 가격 : " + updateFindMenu.getPrice() + " 설명 : " + updateFindMenu.getDescription());
    }

    @Test
    void 할인정책설정() throws Exception {
        MenuCreateReq menuReq = new MenuCreateReq("치즈햄버거", 5000, "치즈가 많아요", "어어러다ㅣ", MenuType.Main);
        MenuRes menuRes = menuService.createMenu(menuReq);
        MenuRes findMenu = menuService.findOne(menuRes.getId());
        System.out.println("생성 메뉴 id : " + findMenu.getId() + " 이름 : " + findMenu.getName() + " 할인 정책 : " + findMenu.getDiscountType());

        menuService.updateDiscountPolicy(menuRes.getId(), DiscountType.FixedRate);
        MenuRes updateDiscountMenu = menuService.findOne(menuRes.getId());
        System.out.println("할인 정책 설정 메뉴 id : " + updateDiscountMenu.getId() + " 이름 : " + updateDiscountMenu.getName() + " 할인 정책 : " + updateDiscountMenu.getDiscountType());
    }
}
