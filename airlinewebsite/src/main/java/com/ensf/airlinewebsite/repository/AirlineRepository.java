package com.ensf.airlinewebsite.repository;

import com.ensf.airlinewebsite.model.Airline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirlineRepository extends JpaRepository<Airline,Integer> {
}