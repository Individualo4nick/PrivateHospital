package com.example.privatehospital.Repositories;

import com.example.privatehospital.Entities.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface RecordRepository extends JpaRepository<Record, Long>, CustomRecordRepository{
    @Modifying
    @Transactional
    @Query(value = "UPDATE Record r SET r.description=:description, r.service=:service, r.price=:price WHERE r.id=:id")
    void updateRecordsField(@Param("id") Long recordId, @Param("description") String description, @Param("service") String service, @Param("price") Integer price);
}
