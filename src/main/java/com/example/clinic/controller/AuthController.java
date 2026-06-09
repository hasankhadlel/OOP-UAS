package com.example.clinic.controller;

import com.example.clinic.entity.Patient;
import com.example.clinic.entity.User;

import com.example.clinic.repository.PatientRepository;
import com.example.clinic.repository.UserRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;

    public AuthController(
            UserRepository userRepository,
            PatientRepository patientRepository
    ) {
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
    }

    @GetMapping("/register")
    public String registerForm(Model model) {

        model.addAttribute(
                "user",
                new User()
        );

        return "register";
    }

    @PostMapping("/register")
    public String registerUser(

            @ModelAttribute User user,

            @RequestParam String fullName,

            @RequestParam String gender,

            @RequestParam String phone,

            @RequestParam String address

    ) {

        user.setRole("PATIENT");

        User savedUser =
                userRepository.save(user);

        Patient patient =
                new Patient();

        patient.setUser(savedUser);

        patient.setFullName(fullName);

        patient.setGender(gender);

        patient.setPhone(phone);

        patient.setAddress(address);

        patientRepository.save(patient);

        return "redirect:/login?registered";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}