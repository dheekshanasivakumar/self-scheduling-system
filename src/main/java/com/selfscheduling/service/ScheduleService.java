package com.selfscheduling.service;

import com.selfscheduling.dto.ScheduleRequest;
import com.selfscheduling.entity.Schedule;
import com.selfscheduling.entity.ScheduleStatus;
import com.selfscheduling.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAllByOrderByScheduleDateAscStartTimeAsc();
    }

    public Schedule getScheduleById(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Schedule not found with id: " + id));
    }

    public List<Schedule> getUpcomingSchedules() {
        return scheduleRepository.findAllByOrderByScheduleDateAscStartTimeAsc()
                .stream()
                .filter(s -> !s.getScheduleDate().isBefore(LocalDate.now()))
                .filter(s -> s.getStatus() == ScheduleStatus.SCHEDULED)
                .toList();
    }

    public long countUpcoming() {
        return scheduleRepository.countUpcoming();
    }

    public long countAll() {
        return scheduleRepository.count();
    }

    public List<Schedule> search(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return getAllSchedules();
        }
        return scheduleRepository.searchByTitleOrName(keyword.trim());
    }

    public List<Schedule> filterByDate(LocalDate date) {
        if (date == null) {
            return getAllSchedules();
        }
        return scheduleRepository.findByScheduleDateOrderByStartTimeAsc(date);
    }

    public Schedule createSchedule(ScheduleRequest request) {
        validateTimes(request.getStartTime().toString(), request.getEndTime().toString(), request);

        Schedule schedule = new Schedule();
        mapRequestToEntity(request, schedule);
        return scheduleRepository.save(schedule);
    }

    public Schedule updateSchedule(Long id, ScheduleRequest request) {
        validateTimes(request.getStartTime().toString(), request.getEndTime().toString(), request);

        Schedule schedule = getScheduleById(id);
        mapRequestToEntity(request, schedule);
        return scheduleRepository.save(schedule);
    }

    public void deleteSchedule(Long id) {
        Schedule schedule = getScheduleById(id);
        scheduleRepository.delete(schedule);
    }

    public Schedule cancelSchedule(Long id) {
        Schedule schedule = getScheduleById(id);
        schedule.setStatus(ScheduleStatus.CANCELLED);
        return scheduleRepository.save(schedule);
    }

    private void mapRequestToEntity(ScheduleRequest request, Schedule schedule) {
        schedule.setName(request.getName());
        schedule.setEmail(request.getEmail());
        schedule.setTitle(request.getTitle());
        schedule.setDescription(request.getDescription());
        schedule.setScheduleDate(request.getScheduleDate());
        schedule.setStartTime(request.getStartTime());
        schedule.setEndTime(request.getEndTime());
        schedule.setCategory(request.getCategory());
        schedule.setGender(request.getGender());
    }

    private void validateTimes(String start, String end, ScheduleRequest request) {
        if (request.getStartTime() != null && request.getEndTime() != null
                && !request.getEndTime().isAfter(request.getStartTime())) {
            throw new IllegalArgumentException("End time should be after start time");
        }
    }
}
