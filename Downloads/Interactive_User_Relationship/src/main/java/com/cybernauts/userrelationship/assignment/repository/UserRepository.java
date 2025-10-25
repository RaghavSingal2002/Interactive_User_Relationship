package com.cybernauts.userrelationship.assignment.repository;

import com.cybernauts.userrelationship.assignment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}

