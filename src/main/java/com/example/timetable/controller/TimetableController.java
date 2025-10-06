package com.example.timetable.controller;

import com.example.timetable.model.Timetable;
import com.example.timetable.service.TimetableService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/timetables")
public class TimetableController {

    private final TimetableService timetableService;

    public TimetableController(TimetableService timetableService) {
        this.timetableService = timetableService;
    }

    /**
     * Creates a new timetable entry after checking for teacher availability.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Timetable createTimetable(@RequestBody Timetable timetable) {
        Long teacherId = timetable.getTeacher().getId();
        String day = timetable.getDayOfWeek();
        String time = timetable.getTimeSlot();
        
        // **RULE: Prevent double-booking a teacher.**
        if (!timetableService.isTeacherAvailable(teacherId, day, time)) {
            // Throw a 409 Conflict error if the teacher is already booked
            throw new ResponseStatusException(
                HttpStatus.CONFLICT, 
                "Teacher (ID: " + teacherId + ") is already assigned to a class on " + day + " at " + time + "."
            );
        }
        return timetableService.saveTimetable(timetable);
    }
    
    /**
     * Fetches timetable for a specific Division.
     * GET /api/timetables/division/1
     */
    @GetMapping("/division/{id}")
    public List<Timetable> getDivisionTimetable(@PathVariable Long id) {
        return timetableService.getTimetableByDivision(id);
    }

    /**
     * Fetches the schedule for a specific Teacher.
     * GET /api/timetables/teacher/5
     */
    @GetMapping("/teacher/{id}")
    public List<Timetable> getTeacherTimetable(@PathVariable Long id) {
        return timetableService.getTimetableByTeacher(id);
    }

    /**
     * Updates an existing timetable entry (PUT) or adds a new one (POST, handled above).
     */
    @PutMapping("/{id}")
    public Timetable updateTimetable(@PathVariable Long id, @RequestBody Timetable updatedTimetable) {
        // Ensure the ID in the path matches the entity ID for update logic consistency
        updatedTimetable.setId(id);
        
        // Re-check conflict before saving the update
        Long teacherId = updatedTimetable.getTeacher().getId();
        String day = updatedTimetable.getDayOfWeek();
        String time = updatedTimetable.getTimeSlot();

        // Find the current entry being updated to allow updating its own slot
        Optional<Timetable> existingTimetableOpt = timetableService.getTimetableById(id);
        
        if (existingTimetableOpt.isPresent()) {
            Timetable existingTimetable = existingTimetableOpt.get();
            
            // Check availability, but exclude the current entry's ID from the conflict check logic.
            // Since the current check relies on the repo query 'findByTeacherIdAndDayOfWeekAndTimeSlot', 
            // if the teacher, day, and time slot are the same, it will find exactly one entry (itself). 
            // A safer, more complex service method would be needed here, 
            // but for a simple case, if the ID is different, it means a conflict with another class.
            
            // For simplicity in this base model, we check if the new schedule conflicts with ANY other class.
            if (!timetableService.isTeacherAvailable(teacherId, day, time)) {
                 // Simple check: If the update tries to move the teacher to a slot 
                 // already occupied by a different timetable entry (not itself), throw conflict.
                 List<Timetable> conflicts = timetableService.getTimetableByTeacher(teacherId).stream()
                     .filter(t -> t.getDayOfWeek().equals(day) && t.getTimeSlot().equals(time) && !t.getId().equals(id))
                     .toList();

                 if (!conflicts.isEmpty()) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Update failed: Teacher already assigned to a different class at this time.");
                 }
            }
        } else {
             throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Timetable entry not found for update.");
        }

        return timetableService.saveTimetable(updatedTimetable);
    }
    
    /**
     * Deletes a timetable entry.
     * DELETE /api/timetables/1
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTimetableEntry(@PathVariable Long id) {
        timetableService.deleteTimetable(id);
    }
}
