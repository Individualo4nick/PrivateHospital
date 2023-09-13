package com.example.privatehospital.Listener;

import com.example.privatehospital.Repositories.RecordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EntityListener {

    @EventListener
    public void logSetNullOnRecordsIfDeleteStaff(EntityEvent event){
        log.info("Delete an employee with id " + event.getId().toString() + ", set null in the record");
    }
}
