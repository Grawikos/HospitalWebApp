package pl.dmcs.hello.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="patients")
public class Patient extends User {
    @OneToMany(mappedBy = "patient", fetch = FetchType.EAGER)
    private List<Visit> visitList;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Doctor familyDoctor;


    public List<Visit> getVisitList() {
        return visitList;
    }

    public void setVisitList(List<Visit> visitList) {
        this.visitList = visitList;
    }

    public Doctor getFamilyDoctor() {
        return familyDoctor;
    }

    public void setFamilyDoctor(Doctor familyDoctor) {
        this.familyDoctor = familyDoctor;
    }

    public Patient() {
    }
}