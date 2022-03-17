package com.example.trip.repository.plan;

import com.example.trip.domain.Plan;
import com.example.trip.dto.response.PlanResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long>, PlanRepositoryCustom {
    @Query("select distinct p from Plan p, Member  m left join fetch p.user left join fetch p.members where m.active =true and m.user.id=:userId order by p.travel_start asc ")
    List<Plan> findPlanAndMembers(@Param("userId") Long userId);

    @Query("select distinct p from Plan p left join fetch p.user left join fetch p.members where p.id =:planId")
    PlanResponseDto findPlanAndMemberOne(@Param("planId") Long planId);

    @Query("select distinct p from Plan p left join fetch p.members left join fetch p.user where p.id=:planId")
    List<Plan> findPlanDetails(@Param("planId") Long planId);
}
