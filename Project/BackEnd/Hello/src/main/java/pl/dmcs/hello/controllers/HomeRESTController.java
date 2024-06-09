package pl.dmcs.hello.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.dmcs.hello.model.*;
import pl.dmcs.hello.repository.DoctorRepository;
import pl.dmcs.hello.repository.PatientRepository;
import pl.dmcs.hello.repository.UserRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/home")
public class HomeRESTController {

    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;

    @Autowired
    public HomeRESTController(DoctorRepository doctorRepository, UserRepository userRepository, PatientRepository patientRepository) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/doctors")
    public List<Doctor> getDoctors() {
        return this.doctorRepository.findAllByDeleted(false);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/patients")
    public List<Patient> getPatients() {
        return this.patientRepository.findAllByDeleted(false);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public List<User> getUsers() {
        return this.userRepository.findAllByDeleted(false);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
        Optional<User> userOpt = this.userRepository.findById(id);
        if (userOpt.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User user = userOpt.get();
        user.setDeleted(true);
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }




}