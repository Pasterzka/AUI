package pl.pastuszka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pastuszka.entity.League;
import pl.pastuszka.entity.Team;

import java.util.List;
import java.util.UUID;

@Repository
public interface TeamRepository extends JpaRepository<Team, UUID> {
    List<Team> findByLeague(League league);
    List<Team> findByLeagueId(UUID leagueId);
}
