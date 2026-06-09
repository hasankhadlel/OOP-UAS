package com.example.clinic.repository;

import com.example.clinic.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    List<Doctor> findByFullNameContainingIgnoreCase(String fullName);

}