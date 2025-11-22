package pl.pastuszka.repository.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pastuszka.entity.League;
import pl.pastuszka.repository.LeagueRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class LeagueService {
    private final LeagueRepository leagueRepository;

    public LeagueService(LeagueRepository leagueRepository) {
        this.leagueRepository = leagueRepository;
    }

    @Transactional(readOnly = true)
    public Optional<League> findById(UUID id){
        return leagueRepository.findById(id);
    }

    // Ta metoda będzie używana przez LeagueSyncController (PUT)
    public void save(League league){
        leagueRepository.save(league);
    }

    // Ta metoda będzie używana przez LeagueSyncController (DELETE)
    public void deleteById(UUID id){
        if (leagueRepository.existsById(id)) {
            leagueRepository.deleteById(id);
        }
    }
}
