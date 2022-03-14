package com.example.trip.repository;

import com.example.trip.domain.CalendarDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarDetailsRepository extends JpaRepository<CalendarDetails,Long> {
}
