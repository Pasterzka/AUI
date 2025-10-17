package pl.pastuszka.league.service;

import org.springframework.stereotype.Service;
import pl.pastuszka.league.entity.League;
import pl.pastuszka.league.repositories.LeagueRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LeagueService {
    private LeagueRepository leagueRepository;

    public LeagueService(LeagueRepository leagueRepository) {
        this.leagueRepository = leagueRepository;
    }

    public List<League> findAll(){
        return leagueRepository.findAll();
    }

    public Optional<League> findById(UUID id){
        return leagueRepository.findById(id);
    }

    public League save(League league){
        return leagueRepository.save(league);
    }

    public void deleteById(UUID id){
        leagueRepository.deleteById(id);
    }
}
