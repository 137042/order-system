package kr.ac.kumoh.ordersystem.repository;

import kr.ac.kumoh.ordersystem.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Optional<Member> findByEmailAndProvider(String email, String provider);
}
