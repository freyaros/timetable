package com.example.timetable.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Timetable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Time slot details
    private String dayOfWeek;   // e.g., "MONDAY"
    private String timeSlot;    // e.g., "10:00-11:00"

    // Mapped Foreign Keys
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    // Identify the specific class/division this slot is for
    @ManyToOne
    @JoinColumn(name = "division_id")
    private Division division; // e.g., CSE 3rd Year A Division
}