package com.example.clinic.controller;

import com.example.clinic.entity.Appointment;
import com.example.clinic.entity.Patient;

import com.example.clinic.repository.AppointmentRepository;
import com.example.clinic.repository.MedicalRecordRepository;
import com.example.clinic.repository.PatientRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/patients")
public class PatientController {

    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final MedicalRecordRepository medicalRecordRepository;

    public PatientController(
            PatientRepository patientRepository,
            AppointmentRepository appointmentRepository,
            MedicalRecordRepository medicalRecordRepository
    ) {
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
        this.medicalRecordRepository = medicalRecordRepository;
    }

    @GetMapping
    public String listPatients(
            @RequestParam(required = false) String keyword,
            Model model
    ) {

        List<Patient> patients;

        if (keyword != null && !keyword.isEmpty()) {

            patients =
                    patientRepository
                            .findByFullNameContainingIgnoreCase(keyword);

        } else {

            patients =
                    patientRepository.findAll();

        }

        model.addAttribute(
                "patients",
                patients
        );

        model.addAttribute(
                "keyword",
                keyword
        );

        return "patients";
    }

    @GetMapping("/add")
    public String addPatientForm(Model model) {

        model.addAttribute(
                "patient",
                new Patient()
        );

        return "patient-form";
    }

    @PostMapping("/save")
    public String savePatient(
            @ModelAttribute Patient patient
    ) {

        patientRepository.save(patient);

        return "redirect:/patients";
    }

    @GetMapping("/edit/{id}")
    public String editPatient(
            @PathVariable Long id,
            Model model
    ) {

        Patient patient =
                patientRepository
                        .findById(id)
                        .orElseThrow();

        model.addAttribute(
                "patient",
                patient
        );

        return "patient-form";
    }

    @GetMapping("/delete/{id}")
    public String deletePatient(
            @PathVariable Long id
    ) {

        Patient patient =
                patientRepository
                        .findById(id)
                        .orElseThrow();

        List<Appointment> appointments =
                appointmentRepository
                        .findByPatient(patient);

        for (Appointment appointment : appointments) {

            medicalRecordRepository
                    .deleteByAppointment(appointment);

        }

        appointmentRepository
                .deleteByPatient(patient);

        patientRepository
                .delete(patient);

        return "redirect:/patients";
    }

}