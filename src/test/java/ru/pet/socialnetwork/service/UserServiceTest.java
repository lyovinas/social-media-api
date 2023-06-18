package ru.pet.socialnetwork.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.pet.socialnetwork.dto.UserDTO;
import ru.pet.socialnetwork.mapper.UserMapper;
import ru.pet.socialnetwork.model.User;
import ru.pet.socialnetwork.repository.UserRepository;
import ru.pet.socialnetwork.service.userdetails.CustomUserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest extends GenericServiceTest<User, UserDTO> {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public UserServiceTest() {
        mapper = Mockito.mock(UserMapper.class);
        repository = Mockito.mock(UserRepository.class);
        RoleService roleService = Mockito.mock(RoleService.class);
        service = new UserService(roleService, (UserRepository) repository, (UserMapper) mapper, passwordEncoder);
    }


    @Test
    void checkPassword() {
        boolean checkPassword =
                ((UserService) service).checkPassword(
                        "password",
                        CustomUserDetails.builder()
                                .password(passwordEncoder.encode("password"))
                                .build()
                );
        assertTrue(checkPassword);
    }

    @Test
    void getByLogin() {
        Mockito.when(((UserRepository) repository).findByLogin("login_1")).thenReturn(UserTestData.USER_1);
        User user = ((UserService) service).getByLogin("login_1");
        assertEquals(UserTestData.USER_1, user);
    }

    @Test
    void getByEmail() {
        Mockito.when(((UserRepository) repository).findByEmail("email_1")).thenReturn(UserTestData.USER_1);
        User user = ((UserService) service).getByEmail("email_1");
        assertEquals(UserTestData.USER_1, user);
    }

    @Test
    @Override
    void getById() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(UserTestData.USER_1));
        Mockito.when(mapper.toDTO(UserTestData.USER_1)).thenReturn(UserTestData.USER_DTO_1);
        UserDTO userDTO = service.getById(1L);
        assertEquals(UserTestData.USER_DTO_1, userDTO);
    }

    @Test
    @Override
    void create() {
        Mockito.when(repository.save(UserTestData.USER_1)).thenReturn(UserTestData.USER_1);
        Mockito.when(mapper.toDTO(UserTestData.USER_1)).thenReturn(UserTestData.USER_DTO_1);
        Mockito.when(mapper.toEntity(UserTestData.USER_DTO_1)).thenReturn(UserTestData.USER_1);
        UserDTO userDTO = service.create(UserTestData.USER_DTO_1);
        assertEquals(UserTestData.USER_DTO_1, userDTO);
    }

    @Test
    @Override
    void update() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(UserTestData.USER_1));
        Mockito.when(repository.save(UserTestData.USER_1)).thenReturn(UserTestData.USER_1);
        Mockito.when(mapper.toDTO(UserTestData.USER_1)).thenReturn(UserTestData.USER_DTO_1);
        Mockito.when(mapper.toEntity(UserTestData.USER_DTO_1)).thenReturn(UserTestData.USER_1);
        UserDTO userDTO = service.update(1L, UserTestData.USER_DTO_1);
        assertEquals(UserTestData.USER_DTO_1, userDTO);
    }

    @Test
    @Override
    void delete() {
        Mockito.when(repository.existsById(1L)).thenReturn(true);
        boolean deleted = service.delete(1L);
        assertTrue(deleted);
    }
}