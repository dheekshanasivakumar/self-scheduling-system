package com.selfscheduling.repository;

import com.selfscheduling.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findAllByOrderByScheduleDateAscStartTimeAsc();

    List<Schedule> findByScheduleDateOrderByStartTimeAsc(LocalDate scheduleDate);

    @Query("SELECT s FROM Schedule s WHERE " +
           "LOWER(s.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "ORDER BY s.scheduleDate ASC, s.startTime ASC")
    List<Schedule> searchByTitleOrName(@Param("keyword") String keyword);

    @Query("SELECT COUNT(s) FROM Schedule s WHERE s.scheduleDate >= CURRENT_DATE")
    long countUpcoming();
}
