package ecom.userservice.controllers;

import ecom.userservice.models.Role;
import ecom.userservice.dtos.RoleToUserForm;
import ecom.userservice.models.User;
import ecom.userservice.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return null; //ResponseEntity.ok().body(userService.getUser(id));
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
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form) {
        userService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

}

