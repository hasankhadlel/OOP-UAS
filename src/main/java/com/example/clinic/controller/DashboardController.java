package com.example.clinic.controller;

import com.example.clinic.repository.PatientRepository;
import com.example.clinic.repository.DoctorRepository;
import com.example.clinic.repository.AppointmentRepository;
import com.example.clinic.repository.MedicalRecordRepository;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final MedicalRecordRepository medicalRecordRepository;

    public DashboardController(
            PatientRepository patientRepository,
            DoctorRepository doctorRepository,
            AppointmentRepository appointmentRepository,
            MedicalRecordRepository medicalRecordRepository
    ) {
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
        this.medicalRecordRepository = medicalRecordRepository;
    }

    @GetMapping("/")
    public String dashboard(
            Model model,
            Authentication authentication
    ) {

        String username = authentication.getName();

        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        boolean isDoctor = authentication.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_DOCTOR"));

        boolean isPatient = authentication.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_PATIENT"));

        model.addAttribute("username", username);

        model.addAttribute(
                "totalPatients",
                patientRepository.count()
        );

        model.addAttribute(
                "totalDoctors",
                doctorRepository.count()
        );

        model.addAttribute(
                "totalAppointments",
                appointmentRepository.count()
        );

        model.addAttribute(
                "totalMedicalRecords",
                medicalRecordRepository.count()
        );

        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("isDoctor", isDoctor);
        model.addAttribute("isPatient", isPatient);

        return "dashboard";
    }
}