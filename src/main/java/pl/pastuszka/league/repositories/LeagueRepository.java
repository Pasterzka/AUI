package pl.pastuszka.league.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pastuszka.league.entity.League;

import java.util.UUID;

public interface LeagueRepository extends JpaRepository<League, UUID> {

}
