package ru.pet.socialnetwork.service;

import org.springframework.stereotype.Service;
import ru.pet.socialnetwork.model.Role;
import ru.pet.socialnetwork.repository.RoleRepository;

@Service
public class RoleService {

    private final RoleRepository roleRepository;



    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }



    public Role getByTitle(String title) {
        return roleRepository.findByTitle(title);
    }

    public Role create(Role newRole) {
        Role existingRole = getByTitle(newRole.getTitle());
        return existingRole != null
                ? existingRole
                : roleRepository.save(newRole);
    }
}
