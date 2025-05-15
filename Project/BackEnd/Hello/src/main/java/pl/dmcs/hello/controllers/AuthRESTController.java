package pl.dmcs.hello.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.dmcs.hello.message.authRequest.LoginForm;
import pl.dmcs.hello.message.authRequest.SignUpForm;
import pl.dmcs.hello.message.response.JwtResponse;
import pl.dmcs.hello.message.response.ResponseMessage;
import pl.dmcs.hello.model.*;
import pl.dmcs.hello.repository.DoctorRepository;
import pl.dmcs.hello.repository.PatientRepository;
import pl.dmcs.hello.repository.RoleRepository;
import pl.dmcs.hello.repository.UserRepository;
import pl.dmcs.hello.security.jwt.JwtProvider;

import java.util.Collections;


@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/auth")
public class AuthRESTController {

    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Autowired
    public AuthRESTController(DaoAuthenticationProvider daoAuthenticationProvider, UserRepository userRepository, PatientRepository patientRepository, DoctorRepository doctorRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.daoAuthenticationProvider = daoAuthenticationProvider;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {
        Authentication authentication = daoAuthenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new ResponseMessage("Fail -> Username is already taken."), HttpStatus.BAD_REQUEST);
        }

        // Create patient account
        Patient user = new Patient(signUpRequest.getUsername(), passwordEncoder.encode(signUpRequest.getPassword()));
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Fail -> Cause: User Role not found."));
        user.setRoles(Collections.singleton(userRole));

        userRepository.save(user);
        return new ResponseEntity<>(new ResponseMessage("User registered successfully."), HttpStatus.OK);
    }

    @PostMapping("/admin/register-doctor")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registerDoctor(@Valid @RequestBody SignUpForm signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new ResponseMessage("Fail -> Username is already taken."), HttpStatus.BAD_REQUEST);
        }

        // Create doctor account
        Doctor doctor = new Doctor(signUpRequest.getUsername(), passwordEncoder.encode(signUpRequest.getPassword()));
        Role doctorRole = roleRepository.findByName(RoleName.ROLE_DOCTOR)
                .orElseThrow(() -> new RuntimeException("Fail -> Cause: Doctor Role not found."));
        doctor.setRoles(Collections.singleton(doctorRole));

        userRepository.save(doctor);

        return new ResponseEntity<>(new ResponseMessage("Doctor registered successfully."), HttpStatus.OK);
    }

    @PostMapping("/admin/register-admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody SignUpForm signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new ResponseMessage("Fail -> Username is already taken."), HttpStatus.BAD_REQUEST);
        }

        // Create admin account
        User admin = new User(signUpRequest.getUsername(), passwordEncoder.encode(signUpRequest.getPassword()));
        Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Fail -> Cause: Admin Role not found."));
        admin.setRoles(Collections.singleton(adminRole));

        userRepository.save(admin);

        return new ResponseEntity<>(new ResponseMessage("Admin registered successfully."), HttpStatus.OK);
    }
}