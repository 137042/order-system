package kr.ac.kumoh.ordersystem.repository;

import kr.ac.kumoh.ordersystem.domain.OrderMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMenuRepository extends JpaRepository<OrderMenu, Integer> {

}
