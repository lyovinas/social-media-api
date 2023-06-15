package ru.pet.socialnetwork.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@SequenceGenerator(name = "default_gen", sequenceName = "users_seq", allocationSize = 1)
public class User extends GenericModel {

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_users_roles"), nullable = false)
    private Role role;

//    @OneToMany(mappedBy = "user")
//    private Set<Post> posts;
}
