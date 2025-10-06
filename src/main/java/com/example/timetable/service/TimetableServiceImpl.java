package com.example.timetable.service;

import com.example.timetable.model.Timetable;
import com.example.timetable.repository.TimetableRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TimetableServiceImpl implements TimetableService {

    private final TimetableRepository timetableRepository;

    public TimetableServiceImpl(TimetableRepository timetableRepository) {
        this.timetableRepository = timetableRepository;
    }

    @Override
    public Timetable saveTimetable(Timetable timetable) {
        return timetableRepository.save(timetable);
    }

    @Override
    public Optional<Timetable> getTimetableById(Long id) {
        return timetableRepository.findById(id);
    }

    @Override
    public void deleteTimetable(Long id) {
        timetableRepository.deleteById(id);
    }

    @Override
    public boolean isTeacherAvailable(Long teacherId, String dayOfWeek, String timeSlot) {
        // Find if any timetable entry exists for this teacher at this exact time/day
        List<Timetable> conflicts = timetableRepository
            .findByTeacherIdAndDayOfWeekAndTimeSlot(teacherId, dayOfWeek, timeSlot);
        
        // If conflicts list is empty, the teacher is available (true)
        return conflicts.isEmpty();
    }

    @Override
    public List<Timetable> getTimetableByDivision(Long divisionId) {
        return timetableRepository.findByDivisionId(divisionId);
    }

    @Override
    public List<Timetable> getTimetableByTeacher(Long teacherId) {
        return timetableRepository.findByTeacherId(teacherId);
    }
}
