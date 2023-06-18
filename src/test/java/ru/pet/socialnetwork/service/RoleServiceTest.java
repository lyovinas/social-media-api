package ru.pet.socialnetwork.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.pet.socialnetwork.model.Role;
import ru.pet.socialnetwork.repository.RoleRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoleServiceTest {

    private final RoleRepository roleRepository;
    private final RoleService roleService;
    private final Role existingRole = new Role("test", "");


    RoleServiceTest() {
        roleRepository = Mockito.mock(RoleRepository.class);
        roleService = new RoleService(roleRepository);
    }


    @Test
    void getByTitle() {
        Mockito.when(roleRepository.findByTitle("test")).thenReturn(existingRole);
        Role foundRole = roleService.getByTitle("test");
        assertEquals(foundRole, existingRole);
    }

    @Test
    void create() {
        Role newRole = new Role();
        Mockito.when(roleRepository.findByTitle("test")).thenReturn(existingRole);
        Mockito.when(roleRepository.save(newRole)).thenReturn(existingRole);
        Role createdRole = roleService.create(newRole);
        assertEquals(createdRole, existingRole);
        createdRole = roleService.create(existingRole);
        assertEquals(createdRole, existingRole);
    }
}