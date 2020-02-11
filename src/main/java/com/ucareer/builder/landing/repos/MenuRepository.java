package com.ucareer.builder.landing.repos;

import com.ucareer.builder.landing.models.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

}