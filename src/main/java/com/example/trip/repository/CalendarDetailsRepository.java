package com.example.trip.repository;

import com.example.trip.domain.Calendar;
import com.example.trip.domain.CalendarDetails;
import com.example.trip.dto.response.CalendarResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CalendarDetailsRepository extends JpaRepository<CalendarDetails,Long> {
    @Modifying
    @Query("delete from CalendarDetails c where c.calendar.id = :calendarId")
    void deleteByCalendarId(@Param("calendarId") Long calendarId);

    @Query("select distinct c from CalendarDetails c left join fetch c.calendar where c.calendar.plan.id =:planId")
    List<CalendarDetails> findByPlanIdAndCalendarId(@Param("planId") Long planId);

    @Query("select c from CalendarDetails c where c.calendar.id =:calendarId")
    List<CalendarDetails> findByCalendarId(Long calendarId);

    @Query("select c from CalendarDetails c where c.name =:location and c.memo=:locationMemo and c.latitude =:latitude and c.longitude =:longitude and c.sort =:sort")
    List<CalendarDetails> findByLocationAndLocationMemo(String location, String locationMemo, String latitude, String longitude, int sort);
}
