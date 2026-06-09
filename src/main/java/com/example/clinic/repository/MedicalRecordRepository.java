package com.example.clinic.repository;

import com.example.clinic.entity.Appointment;
import com.example.clinic.entity.MedicalRecord;
import com.example.clinic.entity.Patient;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicalRecordRepository
        extends JpaRepository<MedicalRecord, Long> {

    List<MedicalRecord> findByAppointmentPatient(
            Patient patient
    );

    void deleteByAppointment(
            Appointment appointment
    );

}