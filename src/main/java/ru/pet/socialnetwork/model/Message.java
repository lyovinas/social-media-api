package ru.pet.socialnetwork.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "messages")
@Getter
@Setter
@SequenceGenerator(name = "default_gen", sequenceName = "messages_seq", allocationSize = 1)
public class Message extends GenericModel {

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_messages_users_1"), nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_messages_users_2"), nullable = false)
    private User targetUser;

    @Column(nullable = false)
    private String text;
}
