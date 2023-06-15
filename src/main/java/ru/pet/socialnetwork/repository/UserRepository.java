package ru.pet.socialnetwork.repository;

import ru.pet.socialnetwork.model.User;

public interface UserRepository extends GenericRepository<User>{

    User findByLogin(String login);

    User findByEmail(String email);
}
