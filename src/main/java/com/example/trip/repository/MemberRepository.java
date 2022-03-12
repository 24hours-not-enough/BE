package com.example.trip.repository;

import com.example.trip.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("select m from Member m where m.plan.id =:plan_id and m.email =:email")
    Optional<Member> findByEmail(@Param("email") String email,@Param("plan_id") Long plan_id);

    @Query("select m from Member m left join fetch m.user left join fetch m.plan where m.plan.id=:planId ")
    List<Member> findPlanAndMembers(@Param("planId") Long planId);
}
