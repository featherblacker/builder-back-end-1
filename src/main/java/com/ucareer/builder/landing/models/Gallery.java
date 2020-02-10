package com.ucarrer.builder.landing.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
@Table(name = "galleries")
public class Gallery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "varchar(255) default ''")
    private String title;

    @Column(columnDefinition = "varchar(255) default ''")
    private String description;

    @Temporal(TIMESTAMP)
    @Column(name = "modified_at", nullable = false, columnDefinition = "TIMESTAMP  default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
    @UpdateTimestamp
    private Date modifiedAt;

    @OneToMany(
            mappedBy = "gallery",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<GalleryItem> galleryItem;

    @OneToOne(mappedBy = "gallery",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnore
    private Landing landing;

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

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Set<GalleryItem> getGalleryItem() {
        return galleryItem;
    }

    public void setGalleryItem(Set<GalleryItem> galleryItem) {
        this.galleryItem = galleryItem;
    }

    public Landing getLanding() {
        return landing;
    }

    public void setLanding(Landing landing) {
        this.landing = landing;
    }
}
