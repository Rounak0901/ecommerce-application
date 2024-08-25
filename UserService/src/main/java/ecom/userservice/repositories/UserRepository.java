package ecom.userservice.repositories;

import ecom.userservice.models.Role;
import ecom.userservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findById(Long id);
    List<User> findUserByRolesIn(Set<Role> roles);
}

