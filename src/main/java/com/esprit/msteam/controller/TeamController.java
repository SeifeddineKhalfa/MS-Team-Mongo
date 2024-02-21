package com.esprit.msteam.controller;


import com.esprit.msteam.persistence.entities.Team;
import com.esprit.msteam.persistence.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/teamApi")
public class TeamController {

    @Autowired
    TeamRepository teamRepository;



    @PostMapping("/teams")
    public ResponseEntity<Team> createTeam(@RequestBody Team team) {
        try {
            Team _team = teamRepository.save(new Team(team.getName(), team.getDepartment()));
            return new ResponseEntity<>(_team, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/teams")
    public ResponseEntity<List<Team>> getAllTeams() {
        try {
            List<Team> teams = new ArrayList<>();


            teamRepository.findAll().forEach(teams::add);


            if (teams.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(teams, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/teams/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable("id") String id) {
        Optional<Team> teamData = teamRepository.findById(id);

        if (teamData.isPresent()) {
            return new ResponseEntity<>(teamData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/teams/published/{department}")
    public ResponseEntity<List<Team>> findByDepartment(@PathVariable("department") String department) {
        try {
            List<Team> teams = teamRepository.findByDepartment(department);

            if (teams.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(teams, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/teams/{id}")
    public ResponseEntity<Team> updateTeam(@PathVariable("id") String id, @RequestBody Team team) {
        Optional<Team> teamData = teamRepository.findById(id);

        if (teamData.isPresent()) {
            Team _team = teamData.get();
            _team.setName(team.getName());
            _team.setDepartment(team.getDepartment());

            return new ResponseEntity<>(teamRepository.save(_team), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/teams/{id}")
    public ResponseEntity<HttpStatus> deleteTeam(@PathVariable("id") String id) {
        try {
            teamRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
