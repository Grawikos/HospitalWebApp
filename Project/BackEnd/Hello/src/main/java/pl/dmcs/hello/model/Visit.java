package pl.dmcs.hello.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;
import pl.dmcs.hello.repository.PatientRepository;

@Entity
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnoreProperties(value = "visitList")
    @ManyToOne(fetch = FetchType.EAGER)
    private Patient patient;
    @JsonIgnoreProperties(value ={"appointmentList", "patients"})
    @ManyToOne(fetch = FetchType.EAGER)
    private Doctor doctor;

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Visit() {
    }

    public Visit(Patient patient, Doctor doctor) {
        this.patient = patient;
        this.doctor = doctor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
