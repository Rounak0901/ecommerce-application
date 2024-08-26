package ecom.userservice.controllers;

import ecom.userservice.dtos.*;
import ecom.userservice.exceptions.*;
import ecom.userservice.models.Role;
import ecom.userservice.models.Token;
import ecom.userservice.models.User;
import ecom.userservice.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    /**
     * Constructor for UserController.
     *
     * @param userService the user service to be used by this controller
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Endpoint for user signup.
     *
     * @param user the user details for signup
     * @return ResponseEntity containing the created user details
     * @throws RoleNotFoundException if the role is not found
     * @throws UserAlreadyExistsException if the user already exists
     */
    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody SignUpDto user) throws RoleNotFoundException, UserAlreadyExistsException {
        User user1 = userService.signup(user.getName(), user.getEmail(), user.getPassword());
        return ResponseEntity.ok().body(UserDto.from(user1));
    }

    /**
     * Endpoint for user login.
     *
     * @param user the user login details
     * @return ResponseEntity containing the generated token
     * @throws InvalidLoginCredential if the login credentials are invalid
     */
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginDto user) throws InvalidLoginCredential {
        Token token = userService.login(user.getEmail(), user.getPassword());
        return ResponseEntity.ok().body(TokenDto.from(token));
    }

    /**
     * Endpoint for user logout.
     *
     * @param request the logout request containing the token
     * @return ResponseEntity indicating the logout status
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody LogoutDto request) {
        userService.logout(request.getToken());
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint to validate a token.
     *
     * @param token the token to be validated
     * @return ResponseEntity containing the user details if the token is valid
     * @throws InvalidTokenException if the token is invalid
     */
    @GetMapping("/validate/{token}")
    public ResponseEntity<UserDto> validate(@PathVariable String token) throws InvalidTokenException {
        User user = userService.validateToken(token);
        return ResponseEntity.ok().body(UserDto.from(user));
    }

}