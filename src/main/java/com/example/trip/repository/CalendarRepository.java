package com.example.trip.repository;

import com.example.trip.domain.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CalendarRepository extends JpaRepository<Calendar,Long> {
    @Query("select distinct c from Calendar c left join fetch c.calendarDetails where c.plan.id =:planId")
    List<Calendar> findByPlanIdAndCalendarId(@Param("planId") Long planId);

    @Query("select c from Calendar c where c.plan.id= :planId")
    List<Calendar> findByPlan(Long planId);

    @Query("select c from Calendar c where c.plan.id =:planId and c.is_locked =true ")
    Optional<Calendar> findByPlanLock(Long planId);
}
