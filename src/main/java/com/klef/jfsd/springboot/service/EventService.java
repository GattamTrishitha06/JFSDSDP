package com.klef.jfsd.springboot.service;

import com.klef.jfsd.springboot.model.Event;
import com.klef.jfsd.springboot.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public Event addEvent(Event event) {
        return eventRepository.save(event);
    }
}
