package com.example.trip.repository;

import com.example.trip.domain.PlanLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanLocationRepository extends JpaRepository<PlanLocation, Long> {
}
