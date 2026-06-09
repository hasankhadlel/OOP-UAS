package com.example.clinic.controller;

import com.example.clinic.entity.Doctor;
import com.example.clinic.repository.DoctorRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorRepository doctorRepository;

    public DoctorController(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @GetMapping
    public String listDoctors(
            @RequestParam(required = false) String keyword,
            Model model
    ) {

        List<Doctor> doctors;

        if (keyword != null && !keyword.isEmpty()) {

            doctors = doctorRepository
                    .findByFullNameContainingIgnoreCase(keyword);

        } else {

            doctors = doctorRepository.findAll();

        }

        model.addAttribute("doctors", doctors);
        model.addAttribute("keyword", keyword);

        return "doctors";
    }

    @GetMapping("/add")
    public String addDoctor(Model model) {

        model.addAttribute(
                "doctor",
                new Doctor()
        );

        return "doctor-form";
    }

    @PostMapping("/save")
    public String saveDoctor(
            @ModelAttribute Doctor doctor
    ) {

        doctorRepository.save(doctor);

        return "redirect:/doctors";
    }

    @GetMapping("/edit/{id}")
    public String editDoctor(
            @PathVariable Long id,
            Model model
    ) {

        Doctor doctor =
                doctorRepository
                        .findById(id)
                        .orElseThrow();

        model.addAttribute(
                "doctor",
                doctor
        );

        return "doctor-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteDoctor(
            @PathVariable Long id
    ) {

        doctorRepository.deleteById(id);

        return "redirect:/doctors";
    }

}