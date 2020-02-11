package com.ucareer.builder.landing.repos;

import com.ucareer.builder.landing.models.Landing;
import com.ucareer.builder.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface LandingRepository extends JpaRepository<Landing, Long> {

}