package com.example.privatehospital.Listener;

import lombok.Getter;

import java.util.EventObject;

@Getter
public class EntityEvent extends EventObject {
    private Long id;
    /**
     * Constructs a prototypical Event.
     *
     * @param entity the object on which the Event initially occurred
     * @param id the staff id
     * @throws IllegalArgumentException if source is null
     */
    public EntityEvent(Object entity, Long id) {
        super(entity);
        this.id = id;
    }
}
