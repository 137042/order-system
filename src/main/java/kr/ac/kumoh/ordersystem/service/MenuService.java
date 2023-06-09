package kr.ac.kumoh.ordersystem.service;

import com.sun.jdi.request.DuplicateRequestException;
import kr.ac.kumoh.ordersystem.domain.DiscountType;
import kr.ac.kumoh.ordersystem.domain.Menu;
import kr.ac.kumoh.ordersystem.domain.MenuType;
import kr.ac.kumoh.ordersystem.dto.MenuCreateReq;
import kr.ac.kumoh.ordersystem.dto.MenuReq;
import kr.ac.kumoh.ordersystem.dto.MenuRes;
import kr.ac.kumoh.ordersystem.mapper.MenuMapper;
import kr.ac.kumoh.ordersystem.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuService {
    private final MenuMapper menuMapper;
    private final MenuRepository menuRepository;

    public List<MenuRes> findAllMenus() {
        List<Menu> menuList = menuRepository.findAll();
        if (menuList.isEmpty()) return new ArrayList<>();

        List<MenuRes> menuResList = new ArrayList<>();

        for (Menu menu : menuList) {
            menuResList.add(menuMapper.toDto(menu));
        }
        return menuResList;
    }

    public MenuRes findOne(Integer menuId){
        Menu menu = menuRepository.findById(menuId)
        .orElseThrow(()-> new NoSuchElementException("존재하지 않는 메뉴입니다."));

        return menuMapper.toDto(menu);
    }

    public MenuRes createMenu(MenuCreateReq req) {
        Menu findMenu = menuRepository.findByName(req.getName());
        if (findMenu != null) {
            throw new DuplicateRequestException("이미 존재하는 이름의 메뉴입니다.");
        }

        Menu menu = menuMapper.toCreateEntity(req);
        menu.updateDiscountType(DiscountType.None);
        MenuRes menuRes = menuMapper.toDto(menuRepository.save(menu));

        return menuRes;
    }
   public Boolean updateMenu(MenuReq req) {
    Menu menu = menuRepository.findById(req.getId())
            .orElseThrow(()-> new NoSuchElementException("존재하지 않는 메뉴입니다."));

    Menu findMenu = menuRepository.findByName(req.getName());
    if (findMenu != null) {
           throw new DuplicateRequestException("이미 존재하는 이름의 메뉴입니다.");
    }
    menu.updateMenu(req);
    return true;
   }

    public int findAllMainCount() {
        List<Menu> menu = menuRepository.findAllMain(MenuType.Main);

        return menu.size();
    }

    public List<MenuRes> findAllMain() {
        List<Menu> menuList = menuRepository.findAllMain(MenuType.Main);

        List<MenuRes> menuResList = new ArrayList<>();

        for (Menu menu : menuList) {
            menuResList.add(menuMapper.toDto(menu));
        }
        return menuResList;
    }
    public Boolean updateDiscountPolicy(Integer menuId, DiscountType type) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(()-> new NoSuchElementException("존재하지 않는 메뉴입니다."));
        menu.updateDiscountType(type);
        return true;
    }
}
