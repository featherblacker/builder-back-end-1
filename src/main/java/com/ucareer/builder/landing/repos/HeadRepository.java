package com.ucareer.builder.landing.repos;

import com.ucareer.builder.landing.models.Head;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface HeadRepository extends JpaRepository<Head, Long> {

}