package ecom.userservice.controllers;

import ecom.userservice.exceptions.RoleNotFoundException;
import ecom.userservice.exceptions.UserNotFoundException;
import ecom.userservice.models.Role;
import ecom.userservice.dtos.RoleToUserForm;
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

    @GetMapping("/user")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) throws UserNotFoundException {
        return ResponseEntity.ok().body(userService.getUser(id));
    }

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

}

