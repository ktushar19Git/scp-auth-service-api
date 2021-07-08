package com.scp.auth.controller;

import com.scp.auth.models.AuthUserDetails;
import com.scp.auth.models.ERole;
import com.scp.auth.models.Role;
import com.scp.auth.models.User;
import com.scp.auth.payloads.request.LoginRequest;
import com.scp.auth.payloads.request.SignUpRequest;
import com.scp.auth.payloads.response.JwtResponse;
import com.scp.auth.payloads.response.MessageResponse;
import com.scp.auth.repository.RoleRepository;
import com.scp.auth.repository.UserRepository;
import com.scp.auth.security.config.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        AuthUserDetails userDetails = (AuthUserDetails) authentication.getPrincipal();
        Set<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toSet());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
        Role roleName = null;
        Optional<User> userAuth = userRepository.findByEmailAddress(signUpRequest.getEmailAddress());
       if(userAuth.isPresent()) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        Optional<Role> role = roleRepository.findByName(ERole.ROLE_ADMIN);
        roleName = role.orElseGet(() -> new Role(ERole.ROLE_ADMIN));
        Set<Role> roles = new HashSet<>();
        roles.add(roleName);


        User user = User.builder()
                .active(true)
                .emailAddress(signUpRequest.getEmailAddress())
                .phoneNumber(signUpRequest.getPhoneNumber())
                .firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .password(encoder.encode(signUpRequest.getPassword()))
                .roles(roles).build();

        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
