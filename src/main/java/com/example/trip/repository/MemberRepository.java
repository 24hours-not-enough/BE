package com.example.trip.repository;

import com.example.trip.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("select m from Member m where m.plan.id =:plan_id and m.user.id =:user_id")
    Optional<Member> findByNickNameAndPlanId(@Param("user_id") Long user_id,@Param("plan_id") Long plan_id);

    @Query("select distinct m from Member m left join fetch m.user left join fetch m.plan where m.plan.id=:planId ")
    List<Member> findPlanAndMembers(@Param("planId") Long planId);

    @Modifying
    @Query("delete from Member m where m.plan.id=:planId and m.room_rep = false")
    void deleteByPlanId(Long planId);

    @Modifying
    @Query("delete from Member m where m.plan.id =:planId and m.user.id=:userId")
    void deleteByPlanAndUser(@Param("planId") Long planId,@Param("userId") Long userId);

    @Query("select m from Member m where m.user.id =:userId and m.active=false")
    List<Member> findByUserMemberList(Long userId);

    @Query("select m from Member m where m.user.id = :userId and m.plan.id =:planId")
    Optional<Member> findByUserAndPland(@Param("userId") Long userId,@Param("planId") Long planId);

    @Query("select m from Member m where m.user.id = :userId and m.plan.id =:planId and m.room_rep = true ")
    Optional<Member> findByAuthMemberAndPlan(@Param("userId") Long userId,@Param("planId") Long planId);

    @Query("select m from Member m where m.user.id =:userId and m.plan.id =:planId and m.active=true")
    Optional<Member> findByUserAndPlanActive(@Param("planId") Long planId, @Param("userId") Long userId);
}
