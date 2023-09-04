package com.example.privatehospital.Repositories;

import com.example.privatehospital.Entities.Record;

public interface CustomRecordRepository {
    Record getRecordById(Long clientRecordId);
}
