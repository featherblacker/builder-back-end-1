package com.ucareer.builder.landing.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
@Table(name = "galleyItems")
public class GalleryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "varchar(255) default ''")
    private String title;

    @Column(columnDefinition = "varchar(255) default ''")
    private String description;

    @Column(columnDefinition = "varchar(255) default ''")
    private String imageUrl;

    @Temporal(TIMESTAMP)
    @Column(name = "modified_at", nullable = false, columnDefinition = "TIMESTAMP  default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
    @UpdateTimestamp
    private Date modifiedAt;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "gallery_id", referencedColumnName = "id")
    @JsonIgnore
    private Gallery gallery;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Gallery getGallery() {
        return gallery;
    }

    public void setGallery(Gallery gallery) {
        this.gallery = gallery;
    }
}
