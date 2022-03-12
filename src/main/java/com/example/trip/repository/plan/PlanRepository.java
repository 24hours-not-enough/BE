package com.example.trip.repository.plan;

import com.example.trip.domain.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long>, PlanRepositoryCustom {
    @Query("select distinct p from Plan p left join fetch p.user left join fetch p.members")
    List<Plan> findPlanAndMembers();
}
