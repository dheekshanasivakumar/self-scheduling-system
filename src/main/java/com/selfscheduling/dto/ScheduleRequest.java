package com.selfscheduling.dto;

import com.selfscheduling.entity.Gender;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO used for creating and updating a Schedule via forms and REST API.
 * Keeps validation logic separate from the JPA entity.
 */
public class ScheduleRequest {

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Please enter a valid email address")
    private String email;

    @NotBlank(message = "Title cannot be empty")
    private String title;

    private String description;

    @NotNull(message = "Date cannot be empty")
    private LocalDate scheduleDate;

    @NotNull(message = "Start time cannot be empty")
    private LocalTime startTime;

    @NotNull(message = "End time cannot be empty")
    private LocalTime endTime;

    private String category;

    @NotNull(message = "Please select a gender")
    private Gender gender;

    public ScheduleRequest() {
    }

    // ---- Getters and Setters ----

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(LocalDate scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
