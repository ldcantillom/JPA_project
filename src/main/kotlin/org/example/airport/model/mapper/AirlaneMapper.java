package org.example.airport.model.mapper;

import org.example.airport.model.dtos.AirlaneDto;
import org.example.airport.model.entities.Airlane;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = FlightMapper.class)
public interface AirlaneMapper {
    @Named("complete")
    @Mapping(source = "airlane.flights", target = "flights", qualifiedByName = "listComplete")
    AirlaneDto toIdDto(Airlane airlane);

    @Mapping(source = "airlaneDto.flights", target = "flights")
    Airlane toEntity(AirlaneDto airlaneDto);

    @Named("listComplete")
    @Mapping(source = "airlane.flights", target = "flights", qualifiedByName = "listComplete")
    List<AirlaneDto> toListIdDto(List<Airlane> airlanes);

    @Mapping(source = "airlaneDto.flights", target = "flights")
    List<Airlane> toListEntity(List<AirlaneDto> airlaneDto);

    @Named("withoutId")
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "airlane.flights", target = "flights", qualifiedByName = "listWithoutId")
    AirlaneDto toDto(Airlane airlane);

    @Named("listWithoutId")
    @Mapping(target = "id",ignore = true)
    @Mapping(source = "airlane.flights", target = "flights", qualifiedByName = "listWithoutId")
    List<AirlaneDto> toListDto(List<Airlane> airlanes);
}