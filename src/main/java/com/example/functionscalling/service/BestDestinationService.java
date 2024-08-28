package com.example.functionscalling.service;

import com.example.functionscalling.domain.Destination;
import com.example.functionscalling.domain.Seasons;
import com.example.functionscalling.repositories.DestinationRepository;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service("getDestinationBySeasons")
@Description("get best destination based on a list of seasons")
public class BestDestinationService implements Function<BestDestinationService.BestDestinationRequest, BestDestinationService.BestDestinationResponse> {

    @Override
    public BestDestinationResponse apply(BestDestinationRequest request) {
        var seasonsList = request.seasons();
        Seasons seasons = Seasons.getValue(seasonsList.getFirst());
        if (seasons == null) return null;
        List<Destination> destinations = destinationRepository.findAllByBestSeason(seasons.name());
        BestDestinationResponse bestDestinationResponse = new BestDestinationResponse(destinations.stream().map(Destination::getCountry).toList());
        return bestDestinationResponse;
    }

    @JsonClassDescription("get destination of a season")
    public record BestDestinationRequest(@JsonProperty(required = true) List<String> seasons) {
    }

    @JsonClassDescription("contains the destinations(countries) to visit based on the seasons")
    public record BestDestinationResponse(List<String> destinations) {
    }

    private final DestinationRepository destinationRepository;

    public BestDestinationService(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }
}
