package com.esprit.msteam.persistence.repositories;

import com.esprit.msteam.persistence.entities.Team;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TeamRepository extends MongoRepository<Team, String> {

    List<Team> findByDepartment(String department);

}
