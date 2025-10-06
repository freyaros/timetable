package com.example.timetable.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a Teacher with a defined weekly workload limit.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    
    // Maximum number of classes a teacher can take per week
    private int maxWeeklyHours;
    

	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}
}
