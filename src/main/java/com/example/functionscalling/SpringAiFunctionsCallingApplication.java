package com.example.functionscalling;

import com.example.functionscalling.domain.Client;
import com.example.functionscalling.domain.Destination;
import com.example.functionscalling.domain.Flight;
import com.example.functionscalling.repositories.ClientRepository;
import com.example.functionscalling.repositories.DestinationRepository;
import com.example.functionscalling.repositories.FlightRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class SpringAiFunctionsCallingApplication {

    Logger log = LoggerFactory.getLogger(SpringAiFunctionsCallingApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringAiFunctionsCallingApplication.class, args);
    }

    @Bean
    ApplicationRunner applicationRunner(DestinationRepository destinationRepository,
                                        ClientRepository clientRepository,
                                        FlightRepository flightRepository) {
        return args -> {
            // Create Client
            clientRepository.saveAll(
                    List.of(
                            new Client(1L, 100L, "John Doe"),
                            new Client(2L, 200L, "Jannet Doe"),
                            new Client(3L, 100L, "Vince Doe")
                    )
            );
            log.info("{} clients added", clientRepository.count());
            //Create Destinations
            destinationRepository.saveAll(
                    List.of(
                            new Destination(1L, "SUMMER", "Cameroun"),
                            new Destination(2L, "WINTER", "Australie"),
                            new Destination(3L, "FALL", "Afrique du Sud"),
                            new Destination(4L, "SPRING", "Angleterre")
                    )
            );
            log.info("{} destinations added", destinationRepository.count());
            // Create flights
            flightRepository.saveAll(
                    List.of(
                            new Flight(100L, LocalDate.of(2024, 10, 28), "Maroua,Cameroun", "Lyon, France", 123),
                            new Flight(200L, LocalDate.of(2025, 1, 4), "Sydney,Australie", "Casablanca, Maroc", 321)
                    )
            );
            log.info("{} flights added", flightRepository.count());

        };
    }

}
