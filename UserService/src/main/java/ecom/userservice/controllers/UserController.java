package ecom.userservice.controllers;

import ecom.userservice.dtos.*;
import ecom.userservice.exceptions.InvalidTokenException;
import ecom.userservice.exceptions.RoleNotFoundException;
import ecom.userservice.exceptions.UserAlreadyExistsException;
import ecom.userservice.exceptions.UserNotFoundException;
import ecom.userservice.models.Role;
import ecom.userservice.models.Token;
import ecom.userservice.models.User;
import ecom.userservice.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //Admin based operations
    @GetMapping("/user")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) throws UserNotFoundException {
        return ResponseEntity.ok().body(userService.getUser(id));
    }

    @GetMapping("/user")
    public ResponseEntity<List<User>> getUsers(@RequestBody List<Role> roles){
        return  ResponseEntity.ok().body(userService.getUsers(roles));
    }

    @PostMapping("/user")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        return ResponseEntity.ok().body(userService.saveUser(user));
    }

    @PostMapping("/role")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        return ResponseEntity.ok().body(userService.saveRole(role));
    }

    @PostMapping("/role")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form) throws UserNotFoundException, RoleNotFoundException {
        userService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }
    // User based operations
    @PostMapping("/user/signup")
    public ResponseEntity<UserDto> signup(@RequestBody SignUpDto user) throws RoleNotFoundException, UserAlreadyExistsException {
        User user1 = userService.signup(user.getName(), user.getEmail(), user.getPassword());
        return ResponseEntity.ok().body(UserDto.from(user1));
    }

    @PostMapping("/user/login")
    public ResponseEntity<Token> login(@RequestBody LoginDto user) {
        return ResponseEntity.ok().body(userService.login(user.getEmail(), user.getPassword()));
    }

    @PostMapping("/user/logout")
    public ResponseEntity<?> logout(@RequestBody LogoutDto request) {
        userService.logout(request.getToken());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/validate/{token}")
    public ResponseEntity<UserDto> validate(@PathVariable String token) throws InvalidTokenException {
        User user = userService.validateToken(token);
        return ResponseEntity.ok().body(UserDto.from(user));
    }

}

