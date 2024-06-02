package pl.dmcs.hello.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="doctors")
public class Doctor extends User{

    private String speciality;

    @JsonIgnore
    @OneToMany(mappedBy = "doctor", fetch = FetchType.EAGER)
    private List<Visit> appointmentList;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Patient> patients;

    public boolean isEmpty(){
        return (this.username.isEmpty() || this.password.isEmpty());
    }

    public Doctor() {
    }

    public Doctor(String username, String password) {
        super();
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public List<Visit> getAppointmentList() {
        return appointmentList;
    }

    public void setAppointmentList(List<Visit> appointmentList) {
        this.appointmentList = appointmentList;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }
}
