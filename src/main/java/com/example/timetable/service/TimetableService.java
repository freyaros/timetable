package com.example.timetable.service;

import com.example.timetable.model.Timetable;
import java.util.List;
import java.util.Optional;

public interface TimetableService {
    
    // CRUD Operations
    Timetable saveTimetable(Timetable timetable);
    void deleteTimetable(Long id);
    Optional<Timetable> getTimetableById(Long id);

    // Fetch Operations
    List<Timetable> getTimetableByDivision(Long divisionId);
    List<Timetable> getTimetableByTeacher(Long teacherId);

    // **CORE BUSINESS LOGIC**
    /**
     * Checks if a teacher is already assigned to a class at the specified time slot.
     * @return true if the teacher is available (no conflict), false otherwise.
     */
    boolean isTeacherAvailable(Long teacherId, String dayOfWeek, String timeSlot);
}
