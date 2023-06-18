package ru.pet.socialnetwork.service;

import ru.pet.socialnetwork.dto.UserDTO;
import ru.pet.socialnetwork.model.Role;
import ru.pet.socialnetwork.model.User;

public interface UserTestData {

    User USER_1 = new User("email_1", "login_1", "password_1", new Role("USER", ""));

    UserDTO USER_DTO_1 = new UserDTO("email_1", "login_1", "password_1", new Role("USER", ""));
}
