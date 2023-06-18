package ru.pet.socialnetwork.service.userdetails;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import ru.pet.socialnetwork.model.Role;
import ru.pet.socialnetwork.model.User;
import ru.pet.socialnetwork.repository.UserRepository;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class CustomUserDetailsServiceTest {

    private final UserRepository repository;
    private final CustomUserDetailsService service;
    private final String adminUserName = "admin";


    public CustomUserDetailsServiceTest() {
        repository = Mockito.mock(UserRepository.class);
        service = new CustomUserDetailsService(repository);
        try {
            Class<CustomUserDetailsService> serviceClass = CustomUserDetailsService.class;
            Field field = serviceClass.getDeclaredField("adminUserName");
            field.setAccessible(true);
            field.set(service, adminUserName);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void loadUserByUsername() {
        String testLogin = "testUserLogin";
        User user = new User("", testLogin, "", new Role());
        Mockito.when(repository.findByLogin(testLogin)).thenReturn(user);
        UserDetails userDetails = service.loadUserByUsername(adminUserName);
        assertEquals(adminUserName, userDetails.getUsername());
        userDetails = service.loadUserByUsername(testLogin);
        assertEquals(testLogin, userDetails.getUsername());
    }
}