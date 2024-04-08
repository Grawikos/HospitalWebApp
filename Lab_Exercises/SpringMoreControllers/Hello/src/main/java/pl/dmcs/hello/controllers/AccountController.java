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
import pl.dmcs.hello.repository.AddressRepository;
import pl.dmcs.hello.repository.StudentRepository;
import pl.dmcs.hello.repository.TeamRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/account")
public class AccountController {
    private StudentRepository studentRepository;
    private AddressRepository addressRepository;
    private AccountRepository accountRepository;
    private TeamRepository teamRepository;

    @Autowired
    public AccountController(StudentRepository studentRepository, AccountRepository accountRepository, AddressRepository addressRepository, TeamRepository teamRepository) {
        this.studentRepository = studentRepository;
        this.addressRepository = addressRepository;
        this.accountRepository = accountRepository;
        this.teamRepository = teamRepository;
    }
    @GetMapping
    public List<Account> findAllStudents() {return accountRepository.findAll();}

    @GetMapping(value = "/{id}")
    public Account findAccount(@PathVariable("id") long id) {return accountRepository.findById(id);}
    @PutMapping
    public ResponseEntity<Account> updateAccountList(@RequestBody List<Account> updates){
        studentRepository.deleteAll();
        accountRepository.saveAll(updates);
        return new ResponseEntity<Account>(HttpStatus.NO_CONTENT);
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<Account> updateAccount(@RequestBody Account account, @PathVariable("id") long id){
        account.setId(id);
        accountRepository.save(account);
        return new ResponseEntity<Account>(HttpStatus.NO_CONTENT);
    }
    @PostMapping
    public ResponseEntity<Account> addAccount(@RequestBody Account account){
        if(account.getStudent() == null){
            System.out.println("No student provided!");
            return new ResponseEntity<Account>(HttpStatus.BAD_REQUEST);
        }
        Student student = account.getStudent();
        if (student.getAddress() != null) {
            if (student.getAddress().getId() <= 0) {
                addressRepository.save(student.getAddress());
            }
        }
        if (student.getTeamList() != null){
            for(Team team: student.getTeamList()){
                if (team.getId() <= 0){
                    teamRepository.saveAll(student.getTeamList());
                }
            }
        }
        student.setAccount(account);
        studentRepository.save(student);
//        accountRepository.save(account);
        return new ResponseEntity<Account>(account, HttpStatus.CREATED);
    }
    @PatchMapping(value = "/{id}")
    public ResponseEntity<Account> updatePartOfAccount(@RequestBody Map<String, Object> updates, @PathVariable("id") long id){
        Account account = accountRepository.findById(id);
        if(account == null){
            System.out.println("Account not found");
            return new ResponseEntity<Account>(HttpStatus.NOT_FOUND);
        }
        partialUpdate(account, updates);
        return new ResponseEntity<Account>(HttpStatus.NO_CONTENT);
    }

    private void partialUpdate(Account account, Map<String, Object> updates){
        if (updates.containsKey("accountName")) {
            account.setAccountName((String) updates.get("accountName"));
        }
        accountRepository.save(account);
    }

    @DeleteMapping
    public ResponseEntity<Account> deleteAll(){
        studentRepository.deleteAll();
        accountRepository.deleteAll();
        return new ResponseEntity<Account>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Account> deleteAccount(@PathVariable("id") long id){
        Account account = accountRepository.findById(id);
        Student student = account.getStudent();
        long studentID = student.getId();
        studentRepository.delete(studentRepository.findById(studentID));

        return new ResponseEntity<Account>(HttpStatus.NO_CONTENT);
    }
}
