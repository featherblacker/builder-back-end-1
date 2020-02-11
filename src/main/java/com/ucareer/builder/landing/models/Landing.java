package com.ucareer.builder.landing.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ucareer.builder.user.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
@Table(name = "landings")
public class Landing {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Temporal(TIMESTAMP)
    @Column(name = "created_at", nullable = true)
    @CreatedDate
    @CreationTimestamp
    private Date createdAt;

    @JsonIgnore
    @Temporal(TIMESTAMP)
    @Column(name = "modified_at", nullable = true, columnDefinition = "TIMESTAMP  default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
    @UpdateTimestamp
    private Date modifiedAt;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "head_id", referencedColumnName = "id")
    private Head head;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "gallery_id", referencedColumnName = "id")
    private Gallery gallery;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "menu_id", referencedColumnName = "id")
    private Menu menu;

//    @OneToOne(mappedBy = "Landing", cascade = {CascadeType.ALL})
//    @JsonIgnore
//    private User user;

//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    public Gallery getGallery() {
        return gallery;
    }

    public void setGallery(Gallery gallery) {
        this.gallery = gallery;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }


}
