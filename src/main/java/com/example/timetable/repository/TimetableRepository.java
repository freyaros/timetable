package com.example.timetable.repository;

import com.example.timetable.model.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimetableRepository extends JpaRepository<Timetable, Long> {

    /**
     * Custom query to check for teacher conflict.
     * Finds any existing timetable entries for a given teacher at a specific day and time slot.
     */
    List<Timetable> findByTeacherIdAndDayOfWeekAndTimeSlot(
            Long teacherId, String dayOfWeek, String timeSlot);

    /**
     * Fetches all timetable entries for a specific division (used for display).
     */
    List<Timetable> findByDivisionId(Long divisionId);

    /**
     * Fetches all timetable entries for a specific teacher (used for displaying teacher's schedule).
     */
    List<Timetable> findByTeacherId(Long teacherId);
}