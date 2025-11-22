package pl.pastuszka.league.repositories.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional(readOnly = true)
    public List<League> findAll(){
        return leagueRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<League> findById(UUID id){
        return leagueRepository.findById(id);
    }

    public void save(League league){
        leagueRepository.save(league);
    }

    public void deleteById(UUID id){
        if (leagueRepository.existsById(id)) {
            leagueRepository.deleteById(id);
        }else{
            System.out.println("Błędne UUID!");
        }
    }

    @Transactional(readOnly = true)
    public List<League> getAllLeaguesWithTeams() {
        return leagueRepository.findAllWithTeams();
    }
}
