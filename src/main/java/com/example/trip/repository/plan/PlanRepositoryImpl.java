package com.example.trip.repository.plan;

import com.example.trip.domain.*;
import com.example.trip.dto.response.PlanResponseDto;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;



public class PlanRepositoryImpl implements PlanRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    public PlanRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

//    @Override
//    public PlanResponseDto findPlanAndMember(Pageable pageable) {
////        List<Tuple> result = jpaQueryFactory.
////                select(Projections.constructor(PlanResponse.class)
////                        , plan.id, plan.title, plan.travel_destination, plan.travel_start, plan.travel_end, plan.del_tc, plan.user)
////                .from(plan)
////                .join(member).on(plan.id.eq(member.plan.id))
////                .join(user).on(plan.user.id.eq(user.id))
////                .fetch();
////        return new PageImpl<>(result)
////        jpaQueryFactory
////                .select(new QPlanResponse(
////                        plan.id, plan.title, plan.travel_destination, plan.travel_start, plan.travel_end,plan.del_tc,plan.createdAt
////                ))
////                .from()
//        List<Tuple> result = jpaQueryFactory.select(plan,member,user)
//                .from(plan)
//                .join(member).on(plan.id.eq(member.plan.id))
//                .join(user).on(plan.user.id.eq(user.id))
//                .fetch();
//
//        Plan planList = result.stream()
//                .map(r -> r.get(plan))
//                .findFirst()
//                .get();
//
//        List<User> userList = result.stream()
//                .map(r -> r.get(user))
//                .filter(Objects::nonNull)
//                .collect(Collectors.toList());
//
//        System.out.println("planList = " +planList);
//        System.out.println("userList = " + userList);
//
//        return PlanResponseDto.of(userList,planList);
   // }
//
//    @Override
//    public void findPlan(Pageable pageable) {
//
//    }
}
