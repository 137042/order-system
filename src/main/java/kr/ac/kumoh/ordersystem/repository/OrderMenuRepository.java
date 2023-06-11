package kr.ac.kumoh.ordersystem.repository;

import kr.ac.kumoh.ordersystem.domain.Order;
import kr.ac.kumoh.ordersystem.domain.OrderMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderMenuRepository extends JpaRepository<OrderMenu, Integer> {

    // OrderMenu 도메인변경됨
    @Query("select o from OrderMenu o where o.menu.id=:id")
    List<OrderMenu> findAllById(@Param("id") Integer id);

    @Query("SELECT o FROM OrderMenu o JOIN FETCH o.menu WHERE o.order = :order")
    List<OrderMenu> findAllByOrder(@Param("order") Order order);

}
