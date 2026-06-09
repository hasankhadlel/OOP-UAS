package com.example.clinic.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "medical_records")
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @Column(columnDefinition = "TEXT")
    private String complaint;

    @Column(columnDefinition = "TEXT")
    private String diagnosis;

    @Column(columnDefinition = "TEXT")
    private String treatment;

    public MedicalRecord() {}

    public Long getId() {
        return id;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public String getComplaint() {
        return complaint;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }
}