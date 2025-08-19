package com.example.houer_challenge.repository;

import com.example.houer_challenge.model.School;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SchoolRepository extends JpaRepository<School, Long> {
    List<School> findAllByOrderByCsvIndexAsc();

    void deleteByIdIn(List<Long> ids);
}
