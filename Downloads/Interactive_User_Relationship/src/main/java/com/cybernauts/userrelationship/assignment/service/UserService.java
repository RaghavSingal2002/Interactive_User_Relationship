package com.cybernauts.userrelationship.assignment.service;

import com.cybernauts.userrelationship.assignment.entity.User;
import com.cybernauts.userrelationship.assignment.exception.ConflictException;
import com.cybernauts.userrelationship.assignment.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    // Get all users with computed popularity score
    public List<User> getAllUsers() {
        List<User> users = repo.findAll();
        users.forEach(this::computePopularityScore);
        return users;
    }

    // Create new user
    public User createUser(User user) {
        return repo.save(user);
    }

    // Update user
    public User updateUser(String id, User updatedUser) {
        User user = repo.findById(id).orElseThrow();
        user.setUsername(updatedUser.getUsername());
        user.setAge(updatedUser.getAge());
        user.setHobbies(updatedUser.getHobbies());
        return repo.save(user);
    }

    // Delete user only if no friends
    public void deleteUser(String id) {
        User user = repo.findById(id).orElseThrow();
        if (!user.getFriends().isEmpty()) {
            throw new IllegalStateException("Cannot delete user while linked to friends. Unlink first.");
        }
        repo.delete(user);
    }

    // Create mutual friendship
    public void linkUsers(String userId, String friendId) {
        User user = repo.findById(userId).orElseThrow();
        User friend = repo.findById(friendId).orElseThrow();

        // Prevent self-link
        if (user.equals(friend)) {
            throw new IllegalArgumentException("Cannot add yourself as a friend");
        }

        // Prevent duplicate mutual friendship
        if (user.getFriends().contains(friend) || friend.getFriends().contains(user)) {
            throw new ConflictException("Friendship already exists");
        }

        user.getFriends().add(friend);
        friend.getFriendOf().add(user);
        repo.save(user);
        repo.save(friend);
    }

    // Remove mutual friendship
    public void unlinkUsers(String userId, String friendId) {
        User user = repo.findById(userId).orElseThrow();
        User friend = repo.findById(friendId).orElseThrow();

        user.getFriends().remove(friend);
        friend.getFriends().remove(user);
        repo.save(user);
        repo.save(friend);
    }

    // Return graph data
    public Map<String, Object> getGraph() {
        List<User> users = repo.findAll();
        users.forEach(this::computePopularityScore);

        List<Map<String, Object>> nodes = users.stream().map(u -> Map.of(
                "id", u.getId(),
                "username", u.getUsername(),
                "age", u.getAge(),
                "hobbies", u.getHobbies(),
                "friends", u.getFriends().stream().map(User::getId).toList(),
                "createdAt", u.getCreatedAt(),
                "popularityScore", u.getPopularityScore()
        )).toList();

        return Map.of("users", nodes);
    }

    // Popularity Score Formula
    private void computePopularityScore(User user) {
        int friendCount = user.getFriends().size();
        int sharedHobbies = user.getFriends().stream()
                .mapToInt(f -> (int) f.getHobbies().stream().filter(user.getHobbies()::contains).count())
                .sum();
        double score = friendCount + (sharedHobbies * 0.5);
        user.setPopularityScore(score);
    }
}

