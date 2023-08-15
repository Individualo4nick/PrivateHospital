package com.example.privatehospital.Repositories;

import com.example.privatehospital.Entities.Staff;
import com.example.privatehospital.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long>, QuerydslPredicateExecutor<Staff> {
    Staff getByName(String name);
    Staff getStaffById(Long id);
    @Query(value= "SELECT DISTINCT c.position FROM Staff c")
    List<String> getPositions();
    @Query(value= "SELECT DISTINCT c.department FROM Staff c")
    List<String> getDepartments();
}
