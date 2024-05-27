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

    public Doctor() {
    }



}
