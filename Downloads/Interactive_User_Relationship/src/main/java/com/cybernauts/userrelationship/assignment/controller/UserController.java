package com.cybernauts.userrelationship.assignment.controller;


import com.cybernauts.userrelationship.assignment.entity.User;
import com.cybernauts.userrelationship.assignment.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return service.createUser(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable String id, @RequestBody User user) {
        return service.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        service.deleteUser(id);
    }

    @PostMapping("/{id}/link")
    public void linkUser(@PathVariable String id, @RequestParam String friendId) {
        service.linkUsers(id, friendId);
    }

    @DeleteMapping("/{id}/unlink")
    public void unlinkUser(@PathVariable String id, @RequestParam String friendId) {
        service.unlinkUsers(id, friendId);
    }

    @GetMapping("/graph")
    public Map<String, Object> getGraph() {
        return service.getGraph();
    }
}

