package kr.ac.kumoh.ordersystem.repository;

import kr.ac.kumoh.ordersystem.domain.Member;
import kr.ac.kumoh.ordersystem.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{
    @Query("SELECT o FROM Order o WHERE o.status = 'BASKET' and o.member = :member")
    List<Order> findBasket(@Param("member") Member member);
    @Query("select o from Order o")
    List<Order> findAll();
}