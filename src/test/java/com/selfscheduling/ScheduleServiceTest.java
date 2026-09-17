package com.selfscheduling;

import com.selfscheduling.dto.ScheduleRequest;
import com.selfscheduling.entity.Gender;
import com.selfscheduling.repository.ScheduleRepository;
import com.selfscheduling.service.ScheduleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Simple unit test that does not require a database connection.
 * Demonstrates that "mvn test" can validate business logic in isolation.
 */
class ScheduleServiceTest {

    private ScheduleService scheduleService;

    @BeforeEach
    void setUp() {
        ScheduleRepository mockRepository = Mockito.mock(ScheduleRepository.class);
        scheduleService = new ScheduleService(mockRepository);
    }

    @Test
    void createSchedule_shouldRejectEndTimeBeforeStartTime() {
        ScheduleRequest request = new ScheduleRequest();
        request.setName("Test User");
        request.setEmail("test@example.com");
        request.setTitle("Test Schedule");
        request.setScheduleDate(LocalDate.now().plusDays(1));
        request.setStartTime(LocalTime.of(14, 0));
        request.setEndTime(LocalTime.of(13, 0)); // invalid: before start time
        request.setCategory("Academic");
        request.setGender(Gender.MALE);

        assertThrows(IllegalArgumentException.class, () -> scheduleService.createSchedule(request));
    }
}
