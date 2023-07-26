package com.example.privatehospital.Repositories;

import com.example.privatehospital.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User getByName(String name);
}
