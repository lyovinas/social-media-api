package ru.pet.socialnetwork.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "posts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "default_gen", sequenceName = "posts_seq", allocationSize = 1)
public class Post extends GenericModel {

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_posts_users"), nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String text;

    @Column
    private String imageFileName;
}
