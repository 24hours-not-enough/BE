package com.example.trip.repository;

import com.example.trip.domain.FeedDetail;
import com.example.trip.domain.FeedLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedLocationRepository extends JpaRepository<FeedLocation, Long> {
}
