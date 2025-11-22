package pl.pastuszka.league.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.pastuszka.league.entity.League;

import java.util.List;
import java.util.UUID;

@Repository
public interface LeagueRepository extends JpaRepository<League, UUID> {

    //@Query("SELECT DISTINCT l FROM League l LEFT JOIN FETCH l.teams")
    //List<League> findAllWithTeams();

}
