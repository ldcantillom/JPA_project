package org.example.airport.model.mapper;

import org.example.airport.model.dtos.FlightDto;
import org.example.airport.model.entities.*;
import org.example.airport.model.repositories.AirlaneRepository;
import org.example.airport.model.repositories.AirportRepository;
import org.example.airport.model.repositories.ClientRepository;
import org.example.airport.model.repositories.ReserveRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class FlightMapper {
    @Autowired
    private AirlaneRepository airlaneRepository;
    @Autowired
    private AirportRepository airportRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ReserveRepository reserveRepository;

    @Named("complete")
    @Mapping(source = "flight.airlane.id",target = "airlane")
    @Mapping(source = "flight.airport.id", target = "airport")
    @Mapping(source = "flight.reserves", target = "reserves")
    public abstract FlightDto toIdDto(Flight flight);

    @Named("listComplete")
    @Mapping(source = "flight.airlane.id",target = "airlane")
    @Mapping(source = "flight.airport.id", target = "airport")
    @Mapping(source = "flight.reserves", target = "reserves")
    public abstract List<FlightDto> toListIdDto(List<Flight> flights);

    @Mapping(source = "flightDto.airlane",target = "airlane")
    @Mapping(source = "flightDto.airport", target = "airport")
    @Mapping(source = "flightDto.reserves", target = "reserves")
    public abstract Flight toEntity(FlightDto flightDto);

    @Mapping(source = "flightDto.airlane",target = "airlane")
    @Mapping(source = "flightDto.airport", target = "airport")
    @Mapping(source = "flightDto.reserves", target = "reserves")
    public abstract List<Flight> toListEntity(List<FlightDto> flightDtos);

    @Named("withoutId")
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "flight.airlane.id",target = "airlane")
    @Mapping(source = "flight.airport.id", target = "airport")
    @Mapping(source = "flight.reserves", target = "reserves")
    public abstract FlightDto toDto(Flight flight);

    @Named("listWithoutId")
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "flight.airlane.id",target = "airlane")
    @Mapping(source = "flight.airport.id", target = "airport")
    @Mapping(source = "flight.reserves", target = "reserves")
    public abstract List<FlightDto> toListDto(List<Flight> flights);

    public Long mapToClientId(Client client){
        return client.getId();
    }

    public Client mapToClient(Long id){
        return clientRepository.findById(id).get();
    }

    public Long mapToAirlaneId(Airlane airlane){
        return airlane.getId();
    }

    public Airlane mapToAirlane(Long id){
        return airlaneRepository.findById(id).get();
    }

    public Long mapToAirportId(Airport airport){
        return airport.getId();
    }

    public Airport mapToAirport(Long id){
        return airportRepository.findById(id).get();
    }

    public Long mapToReserveId(Reserve reserve){
        return reserve.getId();
    }
    public Reserve mapToReserve(Long id){
        return reserveRepository.findById(id).get();
    }

    public List<Reserve> mapToReserveList(List<Long> ids){
        List<Reserve> reserves = new ArrayList<Reserve>();
        for(Long id : ids){
            reserves.add(mapToReserve(id));
        }
        return reserves;
    }
}
