package pl.dmcs.hello.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dmcs.hello.model.Account;
import pl.dmcs.hello.model.Address;
import pl.dmcs.hello.model.Student;
import pl.dmcs.hello.model.Team;
import pl.dmcs.hello.repository.AccountRepository;
import pl.dmcs.hello.repository.StudentRepository;
import pl.dmcs.hello.repository.AddressRepository;
import pl.dmcs.hello.repository.TeamRepository;

import java.util.*;

@RestController
@RequestMapping("/rstudent")
public class StudentRESTController {
    private StudentRepository studentRepository;
    private AddressRepository addressRepository;
    private AccountRepository accountRepository;
    private TeamRepository teamRepository;

    @Autowired
    public StudentRESTController(StudentRepository studentRepository, AddressRepository addressRepository, AccountRepository accountRepository, TeamRepository teamRepository) {
        this.studentRepository = studentRepository;
        this.addressRepository = addressRepository;
        this.accountRepository = accountRepository;
        this.teamRepository = teamRepository;
    }
    @GetMapping
    public List<Student> findAllStudents() {return studentRepository.findAll();}

    @GetMapping(value = "/{id}")
    public Student findStudent(@PathVariable("id") long id) {return studentRepository.findById(id);}
    @PutMapping
    public ResponseEntity<Student> updateStudentList(@RequestBody List<Student> updates){
        studentRepository.deleteAll();
        for (Student student: updates) {
            if (student.getAddress().getId() <= 0) {
                addressRepository.save(student.getAddress());
            }
            for(Team team: student.getTeamList()){
                if (team.getId() <= 0){
                    teamRepository.saveAll(student.getTeamList());
                }
            }
        }

        studentRepository.saveAll(updates);
        return new ResponseEntity<Student>(HttpStatus.NO_CONTENT);
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<Student> updateStudent(@RequestBody Student student, @PathVariable("id") long id){
        student.setId(id);
        if (student.getAddress().getId() <= 0){
            addressRepository.save(student.getAddress());
        }
        for(Team team: student.getTeamList()){
            if (team.getId() <= 0){
                teamRepository.saveAll(student.getTeamList());
            }
        }
        studentRepository.save(student);
        return new ResponseEntity<Student>(HttpStatus.NO_CONTENT);
    }
    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student){
        if (student.getAddress().getId() <= 0){
            addressRepository.save(student.getAddress());
        }
        for(Team team: student.getTeamList()){
            if (team.getId() <= 0){
                teamRepository.saveAll(student.getTeamList());
            }
        }
        studentRepository.save(student);
        return new ResponseEntity<Student>(student, HttpStatus.CREATED);
    }
    @PatchMapping(value = "/{id}")
    public ResponseEntity<Student> updatePartOfStudent(@RequestBody Map<String, Object> updates, @PathVariable("id") long id){
        Student student = studentRepository.findById(id);
        if(student == null){
            System.out.println("Student not found");
            return new ResponseEntity<Student>(HttpStatus.NOT_FOUND);
        }
        partialUpdate(student, updates);
        return new ResponseEntity<Student>(HttpStatus.NO_CONTENT);
    }

    private void partialUpdate(Student student, Map<String, Object> updates){
        if(updates.containsKey("firstname")){
            student.setFirstname((String) updates.get("firstname"));
        }
        if(updates.containsKey("lastname")){
            student.setLastname((String) updates.get("lastname"));
        }
        if(updates.containsKey("email")){
            student.setEmail((String) updates.get("email"));
        }
        if(updates.containsKey("telephone")){
            student.setTelephone((String) updates.get("telephone"));
        }
        if (updates.containsKey("address")) {
            Map<String, Object> addressUpdates = (Map<String, Object>) updates.get("address");
            Address address = student.getAddress();
            if (address == null) {
                address = new Address();
                student.setAddress(address);
            }
            if (addressUpdates.containsKey("street")) {
                address.setStreet((String) addressUpdates.get("street"));
            }
            if (addressUpdates.containsKey("city")) {
                address.setCity((String) addressUpdates.get("city"));
            }
        }
        if (updates.containsKey("account")) {
            Map<String, Object> accountUpdates = (Map<String, Object>) updates.get("account");
            Account account = student.getAccount();
            if (account == null) {
                account = new Account();
                student.setAccount(account);
            }
            if (accountUpdates.containsKey("accountName")) {
                account.setAccountName((String) accountUpdates.get("accountName"));
            }
        }
        if (updates.containsKey("teamList")){
            List<Map<String, Object>> teamUpdates = (List<Map<String, Object>>) updates.get("teamList");
            List<Team> teams = new LinkedList<>();
            for(Map<String, Object> t: teamUpdates){
                Team team = new Team();
                if (t.containsKey("teamName")){
                    team.setTeamName((String) t.get("teamName"));
                    teamRepository.save(team);
                    teams.add(team);
                }
            }
            student.setTeamList(teams);
        }

        studentRepository.save(student);
    }

    @DeleteMapping
    public ResponseEntity<Student> deleteAll(){
        studentRepository.deleteAll();
        return new ResponseEntity<Student>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable("id") long id){
        studentRepository.delete(studentRepository.findById(id));
        return new ResponseEntity<Student>(HttpStatus.NO_CONTENT);
    }
}
