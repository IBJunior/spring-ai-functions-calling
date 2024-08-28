package com.example.functionscalling.repositories;

import com.example.functionscalling.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
