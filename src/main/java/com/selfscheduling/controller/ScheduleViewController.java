package com.selfscheduling.controller;

import com.selfscheduling.dto.ScheduleRequest;
import com.selfscheduling.entity.Gender;
import com.selfscheduling.entity.Schedule;
import com.selfscheduling.service.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@Controller
public class ScheduleViewController {

    private final ScheduleService scheduleService;

    public ScheduleViewController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    // ---- Dashboard ----
    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("totalCount", scheduleService.countAll());
        model.addAttribute("upcomingCount", scheduleService.countUpcoming());
        model.addAttribute("upcomingSchedules", scheduleService.getUpcomingSchedules().stream().limit(5).toList());
        return "dashboard";
    }

    // ---- List / Search / Filter ----
    @GetMapping("/schedules")
    public String listSchedules(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) LocalDate date,
            Model model) {

        java.util.List<Schedule> schedules;
        if (keyword != null && !keyword.isBlank()) {
            schedules = scheduleService.search(keyword);
        } else if (date != null) {
            schedules = scheduleService.filterByDate(date);
        } else {
            schedules = scheduleService.getAllSchedules();
        }

        model.addAttribute("schedules", schedules);
        model.addAttribute("keyword", keyword);
        model.addAttribute("date", date);
        return "schedule-list";
    }

    // ---- Create ----
    @GetMapping("/schedules/new")
    public String showCreateForm(Model model) {
        if (!model.containsAttribute("scheduleRequest")) {
            model.addAttribute("scheduleRequest", new ScheduleRequest());
        }
        model.addAttribute("genders", Gender.values());
        model.addAttribute("formTitle", "Create Schedule");
        model.addAttribute("formAction", "/schedules/new");
        return "schedule-form";
    }

    @PostMapping("/schedules/new")
    public String createSchedule(@Valid @ModelAttribute("scheduleRequest") ScheduleRequest scheduleRequest,
                                  BindingResult bindingResult,
                                  Model model,
                                  RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("genders", Gender.values());
            model.addAttribute("formTitle", "Create Schedule");
            model.addAttribute("formAction", "/schedules/new");
            return "schedule-form";
        }
        try {
            scheduleService.createSchedule(scheduleRequest);
        } catch (IllegalArgumentException ex) {
            model.addAttribute("genders", Gender.values());
            model.addAttribute("formTitle", "Create Schedule");
            model.addAttribute("formAction", "/schedules/new");
            model.addAttribute("errorMessage", ex.getMessage());
            return "schedule-form";
        }
        redirectAttributes.addFlashAttribute("successMessage", "Schedule created successfully!");
        return "redirect:/schedules";
    }

    // ---- Edit ----
    @GetMapping("/schedules/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Schedule schedule = scheduleService.getScheduleById(id);

        ScheduleRequest scheduleRequest = new ScheduleRequest();
        scheduleRequest.setName(schedule.getName());
        scheduleRequest.setEmail(schedule.getEmail());
        scheduleRequest.setTitle(schedule.getTitle());
        scheduleRequest.setDescription(schedule.getDescription());
        scheduleRequest.setScheduleDate(schedule.getScheduleDate());
        scheduleRequest.setStartTime(schedule.getStartTime());
        scheduleRequest.setEndTime(schedule.getEndTime());
        scheduleRequest.setCategory(schedule.getCategory());
        scheduleRequest.setGender(schedule.getGender());

        model.addAttribute("scheduleRequest", scheduleRequest);
        model.addAttribute("genders", Gender.values());
        model.addAttribute("formTitle", "Edit Schedule");
        model.addAttribute("formAction", "/schedules/" + id + "/edit");
        return "schedule-form";
    }

    @PostMapping("/schedules/{id}/edit")
    public String updateSchedule(@PathVariable Long id,
                                  @Valid @ModelAttribute("scheduleRequest") ScheduleRequest scheduleRequest,
                                  BindingResult bindingResult,
                                  Model model,
                                  RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("genders", Gender.values());
            model.addAttribute("formTitle", "Edit Schedule");
            model.addAttribute("formAction", "/schedules/" + id + "/edit");
            return "schedule-form";
        }
        try {
            scheduleService.updateSchedule(id, scheduleRequest);
        } catch (IllegalArgumentException ex) {
            model.addAttribute("genders", Gender.values());
            model.addAttribute("formTitle", "Edit Schedule");
            model.addAttribute("formAction", "/schedules/" + id + "/edit");
            model.addAttribute("errorMessage", ex.getMessage());
            return "schedule-form";
        }
        redirectAttributes.addFlashAttribute("successMessage", "Schedule updated successfully!");
        return "redirect:/schedules";
    }

    // ---- Delete / Cancel ----
    @PostMapping("/schedules/{id}/delete")
    public String deleteSchedule(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        scheduleService.deleteSchedule(id);
        redirectAttributes.addFlashAttribute("successMessage", "Schedule cancelled/deleted.");
        return "redirect:/schedules";
    }

    // ---- Error handling for views ----
    @ExceptionHandler(NoSuchElementException.class)
    public String handleNotFound(NoSuchElementException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }
}
