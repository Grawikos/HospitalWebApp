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
@RequestMapping("/doctor")
@PreAuthorize("hasRole('DOCTOR')")
public class DoctorRESTController {

    private final VisitsRepository visitsRepository;
    private final DoctorRepository doctorRepository;

    private final PatientRepository patientRepository;

    @Autowired
    public DoctorRESTController(VisitsRepository visitsRepository, DoctorRepository doctorRepository, PatientRepository patientRepository) {
        this.visitsRepository = visitsRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    @GetMapping("/visits")
    public List<Visit> getAppointments() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        Long doctorId = userPrinciple.getId();

        return visitsRepository.findByDoctorId(doctorId);
    }

    @PostMapping("/visits")
    public ResponseEntity<?> createVisit(@Valid @RequestBody VisitCreationRequest request) {
        // Get the authenticated user's details
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        Long doctorId = userPrinciple.getId();

        // Ensure the authenticated user is the doctor creating the visit
        if (!doctorId.equals(request.getDoctorId())) {
            return new ResponseEntity<>(new ResponseMessage("Unauthorized to create visit for another doctor"), HttpStatus.UNAUTHORIZED);
        }

        // Retrieve patient and doctor entities
        Optional<Patient> patientOpt = patientRepository.findById(request.getPatientId());
        Optional<Doctor> doctorOpt = doctorRepository.findById(request.getDoctorId());

        if (patientOpt.isEmpty() || doctorOpt.isEmpty()) {
            return new ResponseEntity<>(new ResponseMessage("Patient or Doctor not found"), HttpStatus.NOT_FOUND);
        }

        Visit visit = new Visit();
        visit.setPatient(patientOpt.get());
        visit.setDoctor(doctorOpt.get());
        visit.setAppointmentDate(request.getAppointmentDate());

        visitsRepository.save(visit);

        return new ResponseEntity<>(new ResponseMessage("Visit created successfully"), HttpStatus.OK);
    }

}