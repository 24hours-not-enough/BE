package com.example.trip.repository;

import com.example.trip.domain.LoginLog;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LoginLogRepository extends JpaRepository<LoginLog, Long> {
}
