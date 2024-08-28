package com.example.functionscalling.service;

import com.example.functionscalling.domain.Client;
import com.example.functionscalling.domain.Flight;
import com.example.functionscalling.repositories.ClientRepository;
import com.example.functionscalling.repositories.FlightRepository;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service("getFlightsByUser")
@Description("get user flights based on his userId")
public class FlightsService implements Function<FlightsService.FlightRequest, List<FlightsService.FlightResponse>> {
    @Override
    public List<FlightResponse> apply(FlightRequest flightRequest) {
        Optional<Client> client = clientRepository.findById(Long.parseLong(flightRequest.userId));
        if (client.isEmpty()) return null;
        Optional<Flight> flight = flightRepository.findById(client.get().getFlightId());
        return flight.map(value -> List.of(
                new FlightResponse(value.getDeparture(), value.getDestination(), value.getNumber(), value.getFormattedDate())
        )).orElse(null);
    }

    @JsonClassDescription("Get user flights")
    public static record FlightRequest(String userId) {
    }

    @JsonClassDescription("user flight with destination, flight number and flight date")
    public static record FlightResponse(String departure, String destination, int number, String date) {
    }

    private final FlightRepository flightRepository;
    private final ClientRepository clientRepository;

    public FlightsService(FlightRepository flightRepository, ClientRepository clientRepository) {
        this.flightRepository = flightRepository;
        this.clientRepository = clientRepository;
    }
}
