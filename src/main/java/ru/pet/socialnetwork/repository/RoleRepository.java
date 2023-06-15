package ru.pet.socialnetwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pet.socialnetwork.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByTitle(String title);
}
