package com.selfscheduling.config;

import com.selfscheduling.entity.Gender;
import com.selfscheduling.entity.Schedule;
import com.selfscheduling.entity.ScheduleStatus;
import com.selfscheduling.repository.ScheduleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Inserts a few sample schedules on startup, only if the table is empty.
 * This makes it easy to demo the app immediately after the first run.
 */
@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner seedData(ScheduleRepository scheduleRepository) {
        return args -> {
            if (scheduleRepository.count() > 0) {
                return;
            }

            Schedule s1 = new Schedule();
            s1.setName("Arjun Kumar");
            s1.setEmail("arjun.kumar@example.com");
            s1.setTitle("College Project Meeting");
            s1.setDescription("Discuss progress on the DevOps subject project.");
            s1.setScheduleDate(LocalDate.now().plusDays(1));
            s1.setStartTime(LocalTime.of(10, 0));
            s1.setEndTime(LocalTime.of(11, 0));
            s1.setCategory("Academic");
            s1.setGender(Gender.MALE);
            s1.setStatus(ScheduleStatus.SCHEDULED);

            Schedule s2 = new Schedule();
            s2.setName("Priya Sharma");
            s2.setEmail("priya.sharma@example.com");
            s2.setTitle("Study Session");
            s2.setDescription("Group study for the upcoming semester exams.");
            s2.setScheduleDate(LocalDate.now().plusDays(2));
            s2.setStartTime(LocalTime.of(15, 0));
            s2.setEndTime(LocalTime.of(17, 0));
            s2.setCategory("Study");
            s2.setGender(Gender.FEMALE);
            s2.setStatus(ScheduleStatus.SCHEDULED);

            Schedule s3 = new Schedule();
            s3.setName("Rahul Verma");
            s3.setEmail("rahul.verma@example.com");
            s3.setTitle("Team Discussion");
            s3.setDescription("Sync-up call about the sprint tasks.");
            s3.setScheduleDate(LocalDate.now());
            s3.setStartTime(LocalTime.of(18, 30));
            s3.setEndTime(LocalTime.of(19, 0));
            s3.setCategory("Work");
            s3.setGender(Gender.MALE);
            s3.setStatus(ScheduleStatus.SCHEDULED);

            scheduleRepository.save(s1);
            scheduleRepository.save(s2);
            scheduleRepository.save(s3);
        };
    }
}
