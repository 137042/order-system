package kr.ac.kumoh.ordersystem.service;

import kr.ac.kumoh.ordersystem.domain.Menu;
import kr.ac.kumoh.ordersystem.dto.OrderMenuCountRes;
import kr.ac.kumoh.ordersystem.mapper.OrderMenuMapper;
import kr.ac.kumoh.ordersystem.repository.MenuRepository;
import kr.ac.kumoh.ordersystem.repository.OrderMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderMenuMapper orderMenuMapper;
    private final OrderMenuRepository orderMenuRepository;
    private final MenuRepository menuRepository;

    public List<OrderMenuCountRes> findEachMenuCount(){
        List<Menu> menuList = menuRepository.findAll();

        List<OrderMenuCountRes> orderMenuCountResList = new ArrayList<>();

        for (Menu menu : menuList) {
            try {
                Integer count = orderMenuRepository.findByName(menu.getName());
                orderMenuCountResList.add(orderMenuMapper.toOrderMenuCountRes(menu, count));
            } catch (NullPointerException e) {
                orderMenuCountResList.add(orderMenuMapper.toOrderMenuCountRes(menu, 0));
            }

        }
        return orderMenuCountResList;
    }
}
