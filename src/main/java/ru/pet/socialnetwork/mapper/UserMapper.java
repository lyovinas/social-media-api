package ru.pet.socialnetwork.mapper;

import org.springframework.stereotype.Component;
import ru.pet.socialnetwork.dto.UserDTO;
import ru.pet.socialnetwork.model.User;

@Component
public class UserMapper extends GenericMapper<User, UserDTO> {

    protected UserMapper() {
        super(User.class, UserDTO.class);
    }
}
