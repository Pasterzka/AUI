package pl.pastuszka.league.service;

import pl.pastuszka.league.entity.League;
import pl.pastuszka.league.entity.Team;
import pl.pastuszka.league.repositories.TeamRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TeamService {

    private TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<Team> findByLeagueId(UUID leagueId) {
        return teamRepository.findByLeagueId(leagueId);
    }

    public List<Team> findByLeague(League league) {
        return teamRepository.findByLeague(league);
    }

    public Optional<Team> findById(UUID id) {
        return teamRepository.findById(id);
    }

    public Team save(Team team) {
        return teamRepository.save(team);
    }

    public void deleteById(UUID id) {
        teamRepository.deleteById(id);
    }
}
