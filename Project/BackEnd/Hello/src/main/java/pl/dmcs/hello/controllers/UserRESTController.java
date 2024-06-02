package pl.dmcs.hello.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.dmcs.hello.message.response.ResponseMessage;
import pl.dmcs.hello.message.visitRequest.VisitCreationRequest;
import pl.dmcs.hello.model.Doctor;
import pl.dmcs.hello.model.Patient;
import pl.dmcs.hello.model.Visit;
import pl.dmcs.hello.repository.DoctorRepository;
import pl.dmcs.hello.repository.PatientRepository;
import pl.dmcs.hello.repository.VisitsRepository;
import pl.dmcs.hello.security.services.UserPrinciple;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/user")
@PreAuthorize("hasRole('USER')")
public class UserRESTController {

    private final VisitsRepository visitsRepository;
    private final DoctorRepository doctorRepository;

    private final PatientRepository patientRepository;

    @Autowired
    public UserRESTController(VisitsRepository visitsRepository, DoctorRepository doctorRepository, PatientRepository patientRepository) {
        this.visitsRepository = visitsRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    @GetMapping()
    public List<Visit> getAppointments() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        Long userId = userPrinciple.getId();

        return visitsRepository.findByPatientId(userId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeVisit(@PathVariable("id") long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        Long userId = userPrinciple.getId();
        if (!userId.equals(id)) {
            return new ResponseEntity<>(new ResponseMessage("Unauthorized to create visit for another patient"), HttpStatus.UNAUTHORIZED);
        }
        if (!visitsRepository.existsById(id)){
            return new ResponseEntity<Visit>(HttpStatus.NOT_FOUND);
        }
        visitsRepository.deleteById(id);
        return new ResponseEntity<Visit>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/profile")
    public  ResponseEntity<Patient> updatePatient(@RequestBody Patient updates){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        Long userId = userPrinciple.getId();

        Optional<Patient> patientOpt = patientRepository.findById(userId);
        if(patientOpt.isEmpty()){
            return new ResponseEntity<Patient>(HttpStatus.NOT_FOUND);
        }
        Patient patient = partialUpdate(patientOpt.get(),updates);
        return new ResponseEntity<Patient>(patient, HttpStatus.OK);
    }

    private Patient partialUpdate(Patient patient, Patient updates) {
        if (!updates.getName().isEmpty()) {
            patient.setName(updates.getName());
        }
        if (!updates.getSurname().isEmpty()) {
            patient.setSurname(updates.getSurname());
        }
        if (!updates.getFamilyDoctor().isEmpty()) {
            patient.setFamilyDoctor(updates.getFamilyDoctor());
        }

        patientRepository.save(patient);
        return patient;
    }

}