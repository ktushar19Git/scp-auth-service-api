package com.scp.auth.repository;

import com.scp.auth.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmailAddress(String email);

  //  Boolean existByEmailAddress(String email);

}
