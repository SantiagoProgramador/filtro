package com.riwi.filtro.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.riwi.filtro.domain.entities.Survey;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

}
