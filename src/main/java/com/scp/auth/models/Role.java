package com.scp.auth.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@RequiredArgsConstructor
public class Role {

    @Id
    private String id;

    private final ERole name;
}
