package org.example.airport.model.serviceimplement;

import lombok.AllArgsConstructor;
import org.example.airport.model.dtos.PassengerDto;
import org.example.airport.model.entities.Passenger;
import org.example.airport.model.mapper.PassengerMapper;
import org.example.airport.model.repositories.PassengerRepository;
import org.example.airport.model.repositories.ReserveRepository;
import org.example.airport.model.services.PassengerService;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Lazy))
public class PassengerServiceImpl implements PassengerService {
    private final ReserveRepository reserveRepository;
    private PassengerRepository passengerRepository;
    private PassengerMapper passengerMapper;

    @Override
    public PassengerDto save(PassengerDto passenger) {
        return passengerMapper.toIdDto(passengerRepository.save(passengerMapper.toEntity(passenger)));
    }

    @Override
    public Optional<PassengerDto> getById(int id) {
        return passengerRepository.findById((long) id).map(passengerMapper::toIdDto);
    }

    @Override
    public Optional<PassengerDto> update(int id, PassengerDto passenger) {
        return passengerRepository.findById((long) id).map(oldPassenger ->{
            oldPassenger.setFirstName(passenger.firstName());
            oldPassenger.setLastName(passenger.lastName());
            oldPassenger.setAddress(passenger.address());
            oldPassenger.setCell(passenger.cell());
            oldPassenger.setEmail(passenger.email());
            oldPassenger.setReserve(reserveRepository.findById(passenger.reserve()).get());
            return passengerMapper.toIdDto(passengerRepository.save(oldPassenger));
        });
    }

    @Override
    public List<PassengerDto> findAll() {
        return passengerMapper.toListIdDto(passengerRepository.findAll());
    }

    @Override
    public List<PassengerDto> findByName(String name) {
        Passenger p = new Passenger();
        p.setFirstName(name);
        Example<Passenger> example = Example.of(p);
        return passengerMapper.toListIdDto(passengerRepository.findAll(example));
    }

    @Override
    public void deleteById(int id) {
        passengerRepository.deleteById((long) id);
    }
}
