package pl.pastuszka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pastuszka.entity.League;

import java.util.UUID;

@Repository
public interface LeagueRepository extends JpaRepository<League, UUID> {

}
