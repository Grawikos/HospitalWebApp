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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        Long doctorId = userPrinciple.getId();

        Optional<Patient> patientOpt = patientRepository.findById(request.getPatientId());
        Optional<Doctor> doctorOpt = doctorRepository.findById(doctorId);

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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeVisit(@PathVariable("id") long id) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
//        Long doctorId = userPrinciple.getId();
        System.out.println(id);
        if (!visitsRepository.existsById(id)){
            return new ResponseEntity<Visit>(HttpStatus.NOT_FOUND);
        }
//        if (!doctorId.equals(id)) {
//            return new ResponseEntity<>(new ResponseMessage("Unauthorized to create visit for another patient"), HttpStatus.UNAUTHORIZED);
//        }
        visitsRepository.deleteById(id);
        return new ResponseEntity<Visit>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/patients")
    public List<Patient> getAllPatients() {
        return patientRepository.findAllByDeleted(false);
    }

    @GetMapping("/profile")
    public ResponseEntity<Doctor> getDoctor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        Long doctorId = userPrinciple.getId();
        Optional<Doctor> doctorOpt = doctorRepository.findById(doctorId);
        return doctorOpt.map(doctor -> new ResponseEntity<>(doctor, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PatchMapping("/profile")
    public  ResponseEntity<Doctor> updateDoctor(@RequestBody Doctor updates){
        System.out.println("patch" + updates);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        Long userId = userPrinciple.getId();

        Optional<Doctor> doctorOpt = doctorRepository.findById(userId);
        if(doctorOpt.isEmpty()){
            return new ResponseEntity<Doctor>(HttpStatus.NOT_FOUND);
        }
        Doctor doctor = partialUpdate(doctorOpt.get(),updates);
        return new ResponseEntity<Doctor>(doctor, HttpStatus.OK);
    }

    private Doctor partialUpdate(Doctor doctor, Doctor updates) {
        if (!updates.getName().isEmpty()) {
            doctor.setName(updates.getName());
        }
        if (!updates.getSurname().isEmpty()) {
            doctor.setSurname(updates.getSurname());
        }
        if (!updates.getSpeciality().isEmpty()) {
            doctor.setSpeciality(updates.getSpeciality());
        }

        doctorRepository.save(doctor);
        return doctor;
    }
}