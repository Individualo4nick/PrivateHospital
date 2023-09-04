package com.example.privatehospital.Repositories.Impl;

import com.example.privatehospital.Repositories.CustomCommentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.List;

public class CustomCommentRepositoryImpl implements CustomCommentRepository {
    @PersistenceContext
    EntityManager entityManager;
    @Override
    public List findAllByStaffId(Long staffId) {
        Query query = entityManager.createQuery("FROM Comment WHERE staffId=:param");
        query.setParameter("param", staffId);
        return query.getResultList();
    }
}
