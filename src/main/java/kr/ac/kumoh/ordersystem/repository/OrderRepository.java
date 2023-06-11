package kr.ac.kumoh.ordersystem.repository;

import kr.ac.kumoh.ordersystem.domain.Member;
import kr.ac.kumoh.ordersystem.domain.Order;
import kr.ac.kumoh.ordersystem.domain.OrderStatus;
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
    @Query("SELECT o FROM Order o WHERE o.status != :status AND o.member.id = :member")
    List<Order> findAllByXorStatusAndMember(@Param("status") OrderStatus status, @Param("member") Integer memberId);

    @Query("SELECT o FROM Order o WHERE o.status = :status AND o.member.id = :member")
    List<Order> findAllByStatusAndMember(@Param("status") OrderStatus status, @Param("member") Integer memberId);

    @Query("SELECT o FROM Order o WHERE o.store.id = :id AND o.status != :status")
    List<Order> findAllByStore(@Param("id") Integer id, @Param("status") OrderStatus status);

}