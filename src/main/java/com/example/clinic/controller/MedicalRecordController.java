package com.example.clinic.controller;

import com.example.clinic.entity.Appointment;
import com.example.clinic.entity.MedicalRecord;
import com.example.clinic.entity.Patient;

import com.example.clinic.repository.AppointmentRepository;
import com.example.clinic.repository.MedicalRecordRepository;
import com.example.clinic.repository.PatientRepository;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/medical-records")
public class MedicalRecordController {

    private final MedicalRecordRepository medicalRecordRepository;
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;

    public MedicalRecordController(
            MedicalRecordRepository medicalRecordRepository,
            AppointmentRepository appointmentRepository,
            PatientRepository patientRepository
    ) {
        this.medicalRecordRepository = medicalRecordRepository;
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
    }

    @GetMapping
    public String list(Model model) {

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
                    "records",
                    medicalRecordRepository
                            .findByAppointmentPatient(patient)
            );

        } else {

            model.addAttribute(
                    "records",
                    medicalRecordRepository.findAll()
            );

        }

        return "medical-records";
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping("/add/{appointmentId}")
    public String add(
            @PathVariable Long appointmentId,
            Model model
    ) {

        Appointment appointment =
                appointmentRepository.findById(appointmentId)
                        .orElseThrow();

        model.addAttribute(
                "appointment",
                appointment
        );

        return "medical-record-form";
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping("/save")
    public String save(
            @RequestParam Long appointmentId,
            @RequestParam String diagnosis,
            @RequestParam String treatment
    ) {

        Appointment appointment =
                appointmentRepository.findById(appointmentId)
                        .orElseThrow();

        MedicalRecord record =
                new MedicalRecord();

        record.setAppointment(appointment);

        record.setComplaint(
                appointment.getComplaint()
        );

        record.setDiagnosis(diagnosis);

        record.setTreatment(treatment);

        medicalRecordRepository.save(record);

        appointment.setStatus("COMPLETED");

        appointmentRepository.save(appointment);

        return "redirect:/medical-records";
    }

    @GetMapping("/delete/{id}")
    public String delete(
            @PathVariable Long id
    ) {

        medicalRecordRepository.deleteById(id);

        return "redirect:/medical-records";
    }
}