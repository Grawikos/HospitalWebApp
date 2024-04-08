package pl.dmcs.hello.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dmcs.hello.model.Team;
import pl.dmcs.hello.repository.TeamRepository;
import pl.dmcs.hello.repository.StudentRepository;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/team")
public class TeamController {
    private StudentRepository studentRepository;
    private TeamRepository teamRepository;

    @Autowired
    public TeamController(StudentRepository studentRepository, TeamRepository teamRepository) {
        this.studentRepository = studentRepository;
        this.teamRepository = teamRepository;
    }
    @GetMapping
    public List<Team> findAllStudents() {return teamRepository.findAll();}

    @GetMapping(value = "/{id}")
    public Team findTeam(@PathVariable("id") long id) {return teamRepository.findById(id);}
    @PutMapping
    public ResponseEntity<Team> updateTeamList(@RequestBody List<Team> updates){
        teamRepository.deleteAll();
        teamRepository.saveAll(updates);
        return new ResponseEntity<Team>(HttpStatus.NO_CONTENT);
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<Team> updateTeam(@RequestBody Team team, @PathVariable("id") long id){
        team.setId(id);
        teamRepository.save(team);
        return new ResponseEntity<Team>(HttpStatus.NO_CONTENT);
    }
    @PostMapping
    public ResponseEntity<Team> addTeam(@RequestBody Team team){
        teamRepository.save(team);
        return new ResponseEntity<Team>(team, HttpStatus.CREATED);
    }
    @PatchMapping(value = "/{id}")
    public ResponseEntity<Team> updatePartOfTeam(@RequestBody Map<String, Object> updates, @PathVariable("id") long id){
        Team team = teamRepository.findById(id);
        if(team == null){
            System.out.println("Team not found");
            return new ResponseEntity<Team>(HttpStatus.NOT_FOUND);
        }
        partialUpdate(team, updates);
        return new ResponseEntity<Team>(HttpStatus.NO_CONTENT);
    }

    private void partialUpdate(Team team, Map<String, Object> updates){
        if (updates.containsKey("teamName")) {
            team.setTeamName((String) updates.get("teamName"));
        }
        teamRepository.save(team);
    }

    @DeleteMapping
    public ResponseEntity<Team> deleteAll(){
        teamRepository.deleteAll();
        return new ResponseEntity<Team>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Team> deleteTeam(@PathVariable("id") long id){
        teamRepository.delete(teamRepository.findById(id));
        return new ResponseEntity<Team>(HttpStatus.NO_CONTENT);
    }
}
