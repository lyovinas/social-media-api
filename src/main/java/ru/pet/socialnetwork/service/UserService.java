package ru.pet.socialnetwork.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.pet.socialnetwork.dto.UserDTO;
import ru.pet.socialnetwork.mapper.UserMapper;
import ru.pet.socialnetwork.model.Role;
import ru.pet.socialnetwork.model.User;
import ru.pet.socialnetwork.repository.UserRepository;

import static ru.pet.socialnetwork.constants.UserRolesConstants.USER;

@Service
public class UserService extends GenericService<User, UserDTO> {

    private final RoleService roleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;



    public UserService(RoleService roleService, UserRepository userRepository,
                       UserMapper userMapper,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        repository = userRepository;
        mapper = userMapper;
    }



    @Override
    public UserDTO create(UserDTO userDTO) {
        Role userRole = roleService.getByTitle(USER);
        if (userRole == null) {
            userRole = roleService.create(new Role(USER, "Пользователь"));
        }
        userDTO.setRole(userRole);
        userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        return super.create(userDTO);
    }

    public boolean checkPassword(String password, UserDetails userDetails) {
        return bCryptPasswordEncoder.matches(password, userDetails.getPassword());
    }

    public User getByLogin(String login) {
        return ((UserRepository) repository).findByLogin(login);
    }

    public User getByEmail(String email) {
        return ((UserRepository) repository).findByEmail(email);
    }
}
