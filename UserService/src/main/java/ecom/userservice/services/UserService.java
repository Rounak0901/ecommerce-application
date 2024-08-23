package ecom.userservice.services;

import ecom.userservice.models.Role;
import ecom.userservice.models.User;
import ecom.userservice.repositories.RoleRepository;
import ecom.userservice.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;

    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    public void addRoleToUser(String username, String roleName) {
        User user = userRepository.findByUsername(username).orElseThrow();
        Role role = roleRepository.findByName(roleName).orElseThrow();
        user.getRoles().add(role);
        userRepository.save(user);
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }
}

