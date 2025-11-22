package pl.pastuszka.league.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import pl.pastuszka.league.entity.League;
import pl.pastuszka.league.entity.Team;

import java.util.List;
import java.util.UUID;

@Repository
public interface TeamRepository extends JpaRepository<Team, UUID> {
    List<Team> findByLeagueId(UUID leagueId);
    List<Team> findByLeague(League league);
}
