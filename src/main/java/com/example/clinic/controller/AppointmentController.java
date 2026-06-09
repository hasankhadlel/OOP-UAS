package com.example.clinic.controller;

import com.example.clinic.entity.Appointment;
import com.example.clinic.entity.Doctor;
import com.example.clinic.entity.Patient;

import com.example.clinic.repository.AppointmentRepository;
import com.example.clinic.repository.DoctorRepository;
import com.example.clinic.repository.PatientRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public AppointmentController(
            AppointmentRepository appointmentRepository,
            PatientRepository patientRepository,
            DoctorRepository doctorRepository
    ) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    @GetMapping
    public String listAppointments(Model model) {

        Authentication auth =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String username =
                auth.getName();

        Patient patient =
                patientRepository
                        .findByUserUsername(username)
                        .orElse(null);

        if (patient != null) {

            model.addAttribute(
                    "appointments",
                    appointmentRepository.findByPatient(patient)
            );

        } else {

            model.addAttribute(
                    "appointments",
                    appointmentRepository.findAll()
            );

        }

        return "appointments";
    }

    @GetMapping("/add")
    public String addAppointment(Model model) {

        model.addAttribute(
                "patients",
                patientRepository.findAll()
        );

        model.addAttribute(
                "doctors",
                doctorRepository.findAll()
        );

        return "appointment-form";
    }

    @PostMapping("/save")
    public String saveAppointment(
            @RequestParam Long patientId,
            @RequestParam Long doctorId,
            @RequestParam String appointmentDate,
            @RequestParam String complaint
    ) {

        Patient patient =
                patientRepository.findById(patientId)
                        .orElseThrow();

        Doctor doctor =
                doctorRepository.findById(doctorId)
                        .orElseThrow();

        Appointment appointment =
                new Appointment();

        appointment.setPatient(patient);

        appointment.setDoctor(doctor);

        appointment.setAppointmentDate(
                LocalDateTime.parse(appointmentDate)
        );

        appointment.setComplaint(complaint);

        appointment.setStatus("PENDING");

        appointmentRepository.save(appointment);

        return "redirect:/appointments";
    }

    @GetMapping("/delete/{id}")
    public String deleteAppointment(
            @PathVariable Long id
    ) {

        appointmentRepository.deleteById(id);

        return "redirect:/appointments";
    }

}