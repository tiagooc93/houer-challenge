package com.example.houer_challenge.repository;

import com.example.houer_challenge.model.School;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<School, Long> {
}
