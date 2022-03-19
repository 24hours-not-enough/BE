package com.example.trip.repository;

import com.example.trip.domain.CheckList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CheckListRepository extends JpaRepository<CheckList,Long> {
    void deleteByPlanId(@Param("planId") Long planId);

    @Query("select c from CheckList c where c.plan.id =:planId")
    List<CheckList> findByPlanId(@Param("planId") Long planId);


    @Query("select c from CheckList c where c.plan.id =:planId and c.is_locked = true ")
    Optional<CheckList> findByPlanIdAndLock(@Param("planId") Long planId);
}
