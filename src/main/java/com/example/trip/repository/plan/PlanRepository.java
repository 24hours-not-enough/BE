package com.example.trip.repository.plan;

import com.example.trip.domain.Plan;
import com.example.trip.dto.response.PlanResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan, Long>, PlanRepositoryCustom {
    @Query("select distinct p from Plan p, Member  m left join fetch p.user left join fetch p.members where m.active =true and m.user.id=:userId order by p.travel_start asc ")
    List<Plan> findPlanAndMembers(@Param("userId") Long userId);

    @Query("select distinct p from Plan p left join fetch p.user left join fetch p.members where p.id =:planId")
    PlanResponseDto findPlanAndMemberOne(@Param("planId") Long planId);

    @Query("select distinct p from Plan p inner join Member m on m.user.id=:userId where m.plan.id=p.id order by p.id asc ") //where p.id=:planId
    List<PlanResponseDto.DetailAll> findPlanDetails(@Param("userId") Long userId);

    @Query("select p from Plan p where p.uuid =:roomId")
    Optional<Plan> findByUuid(String roomId);

    @Query("select p from Plan p left join fetch p.user left join fetch p.members m where p.id = :planId and m.active=true and m.user.id =:userId")
    Optional<Plan> findByPlanAndUser(Long planId, Long userId);
}
