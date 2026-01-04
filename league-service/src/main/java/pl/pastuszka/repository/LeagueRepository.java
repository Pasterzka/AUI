package pl.pastuszka.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.pastuszka.entity.League;

@Repository
public interface LeagueRepository extends JpaRepository<League, UUID> {

    //@Query("SELECT DISTINCT l FROM League l LEFT JOIN FETCH l.teams")
    //List<League> findAllWithTeams();

}
