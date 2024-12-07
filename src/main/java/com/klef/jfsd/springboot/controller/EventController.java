package com.klef.jfsd.springboot.controller;

import com.klef.jfsd.springboot.model.Event;
import com.klef.jfsd.springboot.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "http://localhost:3000") // Enable CORS for frontend
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    // Wrapper class for consistent API responses
    static class ApiResponse<T> {
        private boolean success;
        private String message;
        private T data;

        public ApiResponse(boolean success, String message, T data) {
            this.success = success;
            this.message = message;
            this.data = data;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }

        public T getData() {
            return data;
        }
    }

    // Add a new event
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> addEvent(
            @RequestParam("eventName") String eventName,
            @RequestParam("location") String location,
            @RequestParam("date") String date,
            @RequestParam("organization") String organization,
            @RequestParam("image") MultipartFile image) {

        try {
            if (eventName.isBlank() || location.isBlank() || organization.isBlank()) {
                return ResponseEntity.badRequest().body(
                        new ApiResponse<>(false, "Required fields are missing", null));
            }

            if (image.getSize() > 5 * 1024 * 1024) { // Limit image size to 5 MB
                return ResponseEntity.badRequest().body(
                        new ApiResponse<>(false, "Image size exceeds 5MB", null));
            }

            Event event = new Event();
            event.setEventName(eventName);
            event.setLocation(location);
            event.setDate(date);
            event.setOrganization(organization);
            event.setImage(image.getBytes());

            eventRepository.save(event);
            return ResponseEntity.ok(new ApiResponse<>(true, "Event added successfully!", null));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiResponse<>(false, "Error saving event", null));
        }
    }

    // Get all events with optional pagination
    @GetMapping
    public ResponseEntity<ApiResponse<List<Event>>> getAllEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Event> eventPage = eventRepository.findAll(pageable);
        List<Event> events = eventPage.getContent();

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Events fetched successfully", events));
    }

    // Get a single event by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Event>> getEvent(@PathVariable Long id) {
        return eventRepository.findById(id)
                .map(event -> ResponseEntity.ok(new ApiResponse<>(true, "Event fetched successfully", event)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Event not found", null)));
    }

    // Get event image as Base64
 // Get event image as Base64
    @GetMapping("/{id}/image")
    public ResponseEntity<ApiResponse<String>> getEventImage(@PathVariable Long id) {
        return eventRepository.findById(id)
                .map(event -> {
                    String base64Image = Base64.getEncoder().encodeToString(event.getImage());
                    return ResponseEntity.ok(
                            new ApiResponse<>(true, "Image fetched successfully", base64Image));
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Image not found", null)));
    }

    // Update an event
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Event>> updateEvent(
            @PathVariable Long id,
            @RequestBody Event updatedEvent) {

        return eventRepository.findById(id)
                .map(event -> {
                    event.setEventName(updatedEvent.getEventName());
                    event.setLocation(updatedEvent.getLocation());
                    event.setDate(updatedEvent.getDate());
                    event.setOrganization(updatedEvent.getOrganization());
                    if (updatedEvent.getImage() != null) {
                        event.setImage(updatedEvent.getImage());
                    }
                    Event savedEvent = eventRepository.save(event);
                    return ResponseEntity.ok(new ApiResponse<>(true, "Event updated successfully", savedEvent));
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Event not found", null)));
    }

    // Delete an event
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteEvent(@PathVariable Long id) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Event deleted successfully", null));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(false, "Event not found", null));
    }
}
