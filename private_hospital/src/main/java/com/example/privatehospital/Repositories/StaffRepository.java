package com.example.privatehospital.Repositories;

import com.example.privatehospital.Entities.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface StaffRepository extends JpaRepository<Staff, Long>, QuerydslPredicateExecutor<Staff> {
    Staff getByName(String name);
    Staff getStaffById(Long id);
}
