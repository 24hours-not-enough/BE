package com.example.trip.repository;

import com.example.trip.domain.CalendarDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CalendarDetailsRepository extends JpaRepository<CalendarDetails,Long> {
    @Modifying
    @Query("delete from CalendarDetails c where c.calendar.id = :calendarId")
    void deleteByCalendarId(@Param("calendarId") Long calendarId);
}
