package com.example.trip.repository.plan;

import com.example.trip.domain.Plan;
import com.example.trip.dto.response.PlanResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long>, PlanRepositoryCustom {
    @Query("select distinct p from Plan p, Member  m left join fetch p.user left join fetch p.members where m.active =true order by p.travel_start asc ")
    List<Plan> findPlanAndMembers();

    @Query("select p from Plan p left join fetch p.user left join fetch p.members where p.id =:planId")
    PlanResponseDto findPlanAndMemberOne(@Param("planId") Long planId);
}
