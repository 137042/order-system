package kr.ac.kumoh.ordersystem.service;

import kr.ac.kumoh.ordersystem.repository.OrderMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderMenuService {

    private final OrderMenuRepository orderMenuRepository;



}
