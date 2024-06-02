package pl.dmcs.hello.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.dmcs.hello.model.*;
import pl.dmcs.hello.repository.DoctorRepository;

import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/home")
public class HomeRESTController {

    private final DoctorRepository doctorRepository;

    @Autowired
    public HomeRESTController(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @GetMapping()
    public List<Doctor> getDoctors() {
        System.out.println("get doctors");
        return this.doctorRepository.findAll();
    }
}