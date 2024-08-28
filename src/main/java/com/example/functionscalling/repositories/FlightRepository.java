package com.example.functionscalling.repositories;

import com.example.functionscalling.domain.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Long> {
}
