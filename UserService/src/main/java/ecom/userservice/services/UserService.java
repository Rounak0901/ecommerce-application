package ecom.userservice.services;


import ecom.userservice.exceptions.InvalidTokenException;
import ecom.userservice.exceptions.RoleNotFoundException;
import ecom.userservice.exceptions.UserAlreadyExistsException;
import ecom.userservice.exceptions.UserNotFoundException;
import ecom.userservice.models.Role;
import ecom.userservice.models.Token;
import ecom.userservice.models.User;
import ecom.userservice.repositories.RoleRepository;
import ecom.userservice.repositories.TokenRepository;
import ecom.userservice.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TokenRepository tokenRepository;

    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository, RoleRepository roleRepository,TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
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

    public User signup(String name, String email, String password) throws RoleNotFoundException, UserAlreadyExistsException {
        Optional<User> user1 = userRepository.findByUsernameOrEmail(name, email);

        if(user1.isPresent())
            throw new UserAlreadyExistsException("User already exists");

        User user = new User();
        user.setUsername(name);
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setEnabled(true);
        user.setRoles(new HashSet<>(List.of(roleRepository.findByName("USER").orElseThrow(() -> new RoleNotFoundException("Role not found")))));

        return userRepository.save(user);
    }

    public Token login(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && bCryptPasswordEncoder.matches(password, user.get().getPassword())) {
            // Generate and return a token (implementation depends on your token generation logic)
            return generateToken(user.get());
        }
        throw new RuntimeException("Invalid login credentials");
    }

    public void logout(String token) {
        // Invalidate the token
        tokenRepository.findByValue(token).ifPresent(t -> tokenRepository.disable(t.getValue()));
    }

    public Token generateToken(User user){
        LocalDate expiryDate = LocalDate.now().plusDays(30);

        Token token = new Token();
        token.setUser(user);
        token.setValue(RandomStringUtils.randomAlphanumeric(10));
        token.setExpiryAt(Date.from(expiryDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        return tokenRepository.save(token);
    }

    public User validateToken(String token) throws InvalidTokenException {
        return tokenRepository.findByValueAndIsActiveAndExpiryAtGreaterThan(token, true, new Date())
                .map(Token::getUser)
                .orElseThrow(() -> new InvalidTokenException("Invalid token"));
    }
}

