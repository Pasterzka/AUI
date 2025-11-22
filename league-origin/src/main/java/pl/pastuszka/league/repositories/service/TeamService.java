package pl.pastuszka.league.repositories.service;

import org.springframework.stereotype.Service;
import pl.pastuszka.league.entity.League;
import pl.pastuszka.league.entity.Team;
import pl.pastuszka.league.repositories.TeamRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TeamService {

    private TeamRepository teamRepository;

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
        }else{
            System.out.println("Błędne UUID!");
        }
    }


}
