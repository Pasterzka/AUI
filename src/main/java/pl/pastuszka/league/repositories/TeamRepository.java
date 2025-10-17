package pl.pastuszka.league.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pastuszka.league.entity.League;
import pl.pastuszka.league.entity.Team;

import java.util.List;
import java.util.UUID;

public interface TeamRepository extends JpaRepository<League, UUID> {
    List<Team> findByLeagueId(UUID leagueId);
    List<Team> findByLeague(League league);
}
