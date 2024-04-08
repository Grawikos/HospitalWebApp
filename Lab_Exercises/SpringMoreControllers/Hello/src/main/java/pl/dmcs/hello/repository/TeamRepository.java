package pl.dmcs.hello.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dmcs.hello.model.Team;

import java.util.Optional;

@Repository

public interface TeamRepository extends JpaRepository<Team, Long> {
    Team findById (long id);
}
