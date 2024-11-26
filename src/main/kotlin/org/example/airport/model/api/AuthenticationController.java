package org.example.airport.model.api;

import org.example.airport.model.dtos.JwtResponse;
import org.example.airport.model.dtos.LoginRequest;
import org.example.airport.model.dtos.SignUpRequest;
import org.example.airport.model.entities.Client;
import org.example.airport.model.entities.ERole;
import org.example.airport.model.entities.Role;
import org.example.airport.model.repositories.RoleRepository;
import org.example.airport.model.repositories.UserRepository;
import org.example.airport.model.security.jwt.JwtUtil;
import org.example.airport.model.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    public JwtUtil jwtUtil;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username(),
                        loginRequest.password())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken= jwtUtil.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl)authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwtToken, "Bearer", userDetails.getUsername(), roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest sRequest) {
        if (userRepository.existsUserByUsername(sRequest.username())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Error: El nombre de usuario ya está en uso.");
        }

        if (userRepository.existsUserByEmail(sRequest.email())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Error: El correo electrónico ya está en uso.");
        }

        Role defaultRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Rol ROLE_USER no encontrado"));
        Client user = new Client(
                sRequest.username(),
                sRequest.email(),
                passwordEncoder.encode(sRequest.password()),
                new HashSet<>(Set.of(defaultRole))
        );
        Client newUser = userRepository.save(user);
        return ResponseEntity.ok(newUser);
    }
}
