package pl.pastuszka.repository.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pastuszka.entity.League;
import pl.pastuszka.entity.Team;
import pl.pastuszka.repository.TeamRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Transactional(readOnly = true)
    public List<Team> findByLeagueId(UUID leagueId) {
        return teamRepository.findByLeagueId(leagueId);
    }

    @Transactional(readOnly = true)
    public List<Team> findByLeague(League league) {
        return teamRepository.findByLeague(league);
    }

    @Transactional(readOnly = true)
    public Optional<Team> findById(UUID id) {
        return teamRepository.findById(id);
    }

    public void save(Team team) {
        teamRepository.save(team);
    }

    public void deleteById(UUID id) {
        if (teamRepository.existsById(id)) {
            teamRepository.deleteById(id);
        } else {
            System.out.println("Błędne UUID!");
        }
    }

    public List<Team> findAll() {
        return teamRepository.findAll();
    }
}
