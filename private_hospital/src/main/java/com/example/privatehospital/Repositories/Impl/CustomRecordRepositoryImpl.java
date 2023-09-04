package com.example.privatehospital.Repositories.Impl;

import com.example.privatehospital.Entities.Record;
import com.example.privatehospital.Repositories.CustomRecordRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class CustomRecordRepositoryImpl implements CustomRecordRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Record getRecordById(Long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Record> criteriaQuery = criteriaBuilder.createQuery(Record.class);
        Root<Record> root = criteriaQuery.from(Record.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), id.toString()));
        var query = entityManager.createQuery(criteriaQuery);
        return query.getResultList().get(0);
    }
}
