package com.example.demo.repository;

import com.example.demo.enitity.DeveloperEntity;
import com.example.demo.enitity.TeamEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface DeveloperRepository extends CrudRepository<DeveloperEntity, Long> {

    Optional<List<DeveloperEntity>> findByTeamId(TeamEntity teamId);


}
