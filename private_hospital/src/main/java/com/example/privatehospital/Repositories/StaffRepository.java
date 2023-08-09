package com.example.privatehospital.Repositories;

import com.example.privatehospital.Entities.Staff;
import com.example.privatehospital.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
    Staff getByName(String name);
    Staff getStaffById(Long id);
}
