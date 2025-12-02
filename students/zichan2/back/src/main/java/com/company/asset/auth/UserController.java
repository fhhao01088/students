package com.company.asset.auth;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDto> list() {
        AccessGuard.requireAdmin();
        return userService.listUsers();
    }

    @GetMapping("/{id}")
    public UserDto get(@PathVariable Long id) {
        AccessGuard.requireAdmin();
        return userService.getUser(id);
    }

    @PostMapping
    public UserDto create(@Valid @RequestBody CreateUserRequest request) {
        AccessGuard.requireAdmin();
        return userService.createUser(request);
    }

    @PutMapping("/{id}")
    public UserDto update(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
        AccessGuard.requireAdmin();
        return userService.updateUser(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        AccessGuard.requireAdmin();
        userService.deleteUser(id);
    }
}
