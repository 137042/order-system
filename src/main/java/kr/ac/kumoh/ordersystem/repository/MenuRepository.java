package kr.ac.kumoh.ordersystem.repository;

import kr.ac.kumoh.ordersystem.domain.Menu;
import kr.ac.kumoh.ordersystem.domain.MenuType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {
    @Query("select m from Menu m")
    List<Menu> findAll();

    @Query("select m from Menu m where m.type=:menutype")
    List<Menu> findAllMain(@Param("menutype") MenuType menutype);

    @Query("select m from Menu m where m.name=:name")
    Menu findByName(@Param("name") String name);
}
