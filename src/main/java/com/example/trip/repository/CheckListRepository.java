package com.example.trip.repository;

import com.example.trip.domain.CheckList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckListRepository extends JpaRepository<CheckList,Long> {
}
