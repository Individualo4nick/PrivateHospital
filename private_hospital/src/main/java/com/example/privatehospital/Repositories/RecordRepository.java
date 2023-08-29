package com.example.privatehospital.Repositories;

import com.example.privatehospital.Entities.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface RecordRepository extends JpaRepository<Record, Long> {
    Record getRecordByClientRecordId(Long clientRecordId);
    @Modifying
    @Transactional
    @Query(value = "UPDATE Record r SET r.description=:description, r.service=:service, r.price=:price WHERE r.clientRecordId=:clientRecordId")
    void updateRecordsField(@Param("clientRecordId") Long clientRecordId, @Param("description") String description, @Param("service") String service, @Param("price") Integer price);
}
