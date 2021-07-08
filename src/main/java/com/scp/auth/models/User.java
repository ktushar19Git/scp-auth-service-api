package com.scp.auth.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Set;

@Document(collection = "users")
@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {

    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String emailAddress;

    private String phoneNumber;

    private String password;

    private Set<Role> roles;

    private boolean active;
}
