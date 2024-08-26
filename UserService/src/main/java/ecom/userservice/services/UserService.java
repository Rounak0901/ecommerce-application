package ecom.userservice.services;

import ecom.userservice.exceptions.InvalidLoginCredential;
import ecom.userservice.exceptions.InvalidTokenException;
import ecom.userservice.exceptions.RoleNotFoundException;
import ecom.userservice.exceptions.UserAlreadyExistsException;
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
import java.util.*;

@Service
public class UserService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TokenRepository tokenRepository;

    /**
     * Constructor for UserService.
     *
     * @param bCryptPasswordEncoder the BCryptPasswordEncoder to encode passwords
     * @param userRepository the repository to manage User entities
     * @param roleRepository the repository to manage Role entities
     * @param tokenRepository the repository to manage Token entities
     */
    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository, RoleRepository roleRepository, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
    }

    /**
     * Signs up a new user.
     *
     * @param name the username
     * @param email the user's email
     * @param password the user's password
     * @return the created User entity
     * @throws RoleNotFoundException if the role is not found
     * @throws UserAlreadyExistsException if the user already exists
     */
    public User signup(String name, String email, String password) throws RoleNotFoundException, UserAlreadyExistsException {
        if (userRepository.findByUsernameOrEmail(name, email).isPresent()) {
            throw new UserAlreadyExistsException("User already exists");
        }

        User user = new User();
        user.setUsername(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(true);
        user.setRoles(Set.of(roleRepository.findByName("USER").orElseThrow(() -> new RoleNotFoundException("Role not found"))));

        return userRepository.save(user);
    }

    /**
     * Logs in a user.
     *
     * @param email the user's email
     * @param password the user's password
     * @return the generated Token entity
     * @throws InvalidLoginCredential if the login credentials are invalid
     */
    public Token login(String email, String password) throws InvalidLoginCredential {
        User user = userRepository.findByEmail(email)
                .filter(u -> passwordEncoder.matches(password, u.getPassword()))
                .orElseThrow(() -> new InvalidLoginCredential("Invalid login credentials"));

        return generateToken(user);
    }

    /**
     * Logs out a user by invalidating the token.
     *
     * @param token the token to be invalidated
     */
    public void logout(String token) {
        // Invalidate the token
        tokenRepository.findByValue(token).ifPresent(t -> tokenRepository.disable(t.getValue()));
    }

    /**
     * Generates a token for a user.
     *
     * @param user the user for whom the token is generated
     * @return the generated Token entity
     */
    public Token generateToken(User user) {
        LocalDate expiryDate = LocalDate.now().plusDays(30);

        Token token = new Token();
        token.setUser(user);
        token.setValue(RandomStringUtils.randomAlphanumeric(10));
        token.setIsActive(true);
        token.setExpiryAt(Date.from(expiryDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        return tokenRepository.save(token);
    }

    /**
     * Validates a token.
     *
     * @param token the token to be validated
     * @return the User entity associated with the valid token
     * @throws InvalidTokenException if the token is invalid
     */
    public User validateToken(String token) throws InvalidTokenException {
        return tokenRepository.findByValueAndIsActiveAndExpiryAtGreaterThan(token, true, new Date())
                .map(Token::getUser)
                .orElseThrow(() -> new InvalidTokenException("Invalid token"));
    }
}