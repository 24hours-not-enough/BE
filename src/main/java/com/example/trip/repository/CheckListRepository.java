package com.example.trip.repository;

import com.example.trip.domain.CheckList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface CheckListRepository extends JpaRepository<CheckList,Long> {
    void deleteByPlanId(@Param("planId") Long planId);
}
