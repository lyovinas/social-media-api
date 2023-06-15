package ru.pet.socialnetwork.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "subscriptions")
@Getter
@Setter
@SequenceGenerator(name = "default_gen", sequenceName = "subscriptions_seq", allocationSize = 1)
public class Subscription extends GenericModel {

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_subscriptions_users_1"), nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_subscriptions_users_2"), nullable = false)
    private User targetUser;

    @Column
    private Boolean friendly;
}
