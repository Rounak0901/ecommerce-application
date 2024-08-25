package ecom.userservice.services;

import ecom.userservice.exceptions.RoleNotFoundException;
import ecom.userservice.exceptions.UserNotFoundException;
import ecom.userservice.models.Role;
import ecom.userservice.models.User;
import ecom.userservice.repositories.RoleRepository;
import ecom.userservice.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

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

    public void addRoleToUser(String username, String roleName) throws UserNotFoundException, RoleNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(String.format("User not found with username: %s", username)));
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RoleNotFoundException(String.format("Role not found with name: %s", roleName)));
        user.getRoles().add(role);
        userRepository.save(user);
    }

    public User getUser(Long userId) throws UserNotFoundException {
        return userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException(String.format("User not found with id: %s", userId)));
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public List<User> getUsers(List<Role> roles){
        userRepository.findUserByRolesIn(new HashSet<>(roles));
        return null;
    }
}

