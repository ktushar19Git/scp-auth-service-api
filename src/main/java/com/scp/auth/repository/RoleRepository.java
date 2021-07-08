package com.scp.auth.repository;

import com.scp.auth.models.ERole;
import com.scp.auth.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}
