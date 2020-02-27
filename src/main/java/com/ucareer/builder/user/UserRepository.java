package com.ucareer.builder.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
// User -> Table, Long -> type of PK
public interface UserRepository extends JpaRepository<User, Long> {
    // SELECT * FROM User WHERE username = ""
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

}