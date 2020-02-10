package com.ucarrer.builder.landing.repos;

import com.ucarrer.builder.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface LandingRepository extends JpaRepository<Landing, Long> {
    Optional<User> findByUser(User user);

}