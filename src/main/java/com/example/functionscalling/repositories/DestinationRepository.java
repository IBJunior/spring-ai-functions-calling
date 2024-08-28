package com.example.functionscalling.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.functionscalling.domain.Destination;

import java.util.List;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {
    Destination findByBestSeason(String season);

    List<Destination> findAllByBestSeason(String season);
}
