package com.ucareer.builder.landing.repos;

import com.ucareer.builder.landing.models.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GalleryRepository extends JpaRepository<Gallery, Long> {

}