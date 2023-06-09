package kr.ac.kumoh.ordersystem.repository;

import kr.ac.kumoh.ordersystem.domain.Member;
import kr.ac.kumoh.ordersystem.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
    Optional<Member> findByEmailAndProvider(String email, String provider);
  
    @Query("select m from Member m where m.email=:email")
    Member findByEmail(@Param("email") String email);

}
