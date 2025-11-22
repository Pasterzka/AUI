package pl.pastuszka.repository.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate; // Import RestTemplate

import pl.pastuszka.entity.League;
import pl.pastuszka.repository.LeagueRepository;
import pl.pastuszka.repository.dto.LeagueEventDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LeagueService {
    private final LeagueRepository leagueRepository;
    private final RestTemplate restTemplate; // Do komunikacji

    // Adres drugiego serwisu (team-service na porcie 8082)
    private final String TEAM_SERVICE_URL = "http://localhost:8082/api/sync/leagues";

    public LeagueService(LeagueRepository leagueRepository, RestTemplate restTemplate) {
        this.leagueRepository = leagueRepository;
        this.restTemplate = restTemplate;
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

        // POWIADOMIENIE team-service (PUT)
        try {
            restTemplate.put(
                    TEAM_SERVICE_URL + "/" + league.getId(),
                    new LeagueEventDTO(league.getId(), league.getName())
            );
        } catch (Exception e) {
            System.err.println("Nie udało się zsynchronizować z team-service: " + e.getMessage());
        }
    }

    public void deleteById(UUID id){
        if (leagueRepository.existsById(id)) {
            leagueRepository.deleteById(id);

            // POWIADOMIENIE team-service (DELETE)
            try {
                restTemplate.delete(TEAM_SERVICE_URL + "/" + id);
            } catch (Exception e) {
                System.err.println("Nie udało się zsynchronizować z team-service (delete): " + e.getMessage());
            }
        } else {
            System.out.println("Błędne UUID!");
        }
    }
}