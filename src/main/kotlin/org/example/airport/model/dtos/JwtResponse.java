package org.example.airport.model.dtos;

import java.util.List;

public record JwtResponse(String token, String type, String username, List<String> roles) {

}
