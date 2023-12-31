package com.example.authorization.repo;

import com.example.authorization.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> getUserByLogin(String email);
    User findUserByLogin(String login);
}
