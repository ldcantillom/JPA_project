package org.example.airport.model.dtos;

import org.example.airport.model.entities.Reserve;
import org.example.airport.model.entities.Role;

import java.util.List;
import java.util.Set;

public record SignUpRequest (String firstName, String lastName, String address, String cell, String email, List<Reserve> reserves, String username, String password, Set<Role> roles){
}
