package com.scp.auth.payloads.response;

import com.scp.auth.models.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@AllArgsConstructor
@Data
public class JwtResponse {
    private String jwtToken;
    private String id;
    private String userName;
    private String email;
    private Set<String> roles;
}
