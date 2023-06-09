package kr.ac.kumoh.ordersystem.repository;

import kr.ac.kumoh.ordersystem.domain.OrderMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderMenuRepository extends JpaRepository<OrderMenu, Integer> {

    // OrderMenu 도메인변경됨
//    @Query("select sum(o.count) from OrderMenu o where o.menu.name=:name")
//    Integer findByName(@Param("name") String name);
}
