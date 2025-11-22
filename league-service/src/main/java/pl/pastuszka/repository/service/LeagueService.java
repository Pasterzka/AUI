package pl.pastuszka.repository.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate; // Import RestTemplate

import pl.pastuszka.entity.League;
import pl.pastuszka.repository.LeagueRepository;
import pl.pastuszka.repository.dto.LeagueEventDTO;
import pl.pastuszka.repository.dto.LeagueTeamListDTO;
import pl.pastuszka.repository.dto.SimpleTeamDTO;
import pl.pastuszka.repository.dto.TeamListDTO;

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

    public List<LeagueTeamListDTO> getAllLeaguesWithTeams() {
        // 1. Pobierz wszystkie ligi z lokalnej bazy
        List<League> leagues = leagueRepository.findAll();

        // 2. Pobierz wszystkie drużyny z team-service przez REST
        // Uwaga: endpoint w team-service to /leagues/all/teams lub inny, który ustawiłeś.
        // Ale ponieważ w TeamControllerze mamy @RequestMapping("/leagues/{leagueId}/teams"),
        // lepiej dodać osobny, niezależny kontroler w team-service lub użyć hacka w ścieżce.

        // SUGERUJĘ: W team-service użyć osobnego kontrolera np. InternalController dla "/internal/teams"
        // Ale dla uproszczenia załóżmy, że dodałeś w TeamController endpoint dostępny pod ścieżką.

        // Pobieramy tablicę drużyn
        SimpleTeamDTO[] teamsArray = {};
        try {
            // Adres musi pasować do endpointu z Kroku 1.
            // Uwaga: Jeśli TeamController ma @RequestMapping("/leagues/{leagueId}/teams"), to ciężko tam dodać globalny endpoint.
            // LEPIEJ: Dodaj w TeamService osobny "GlobalTeamController" (patrz niżej instrukcja *).

            teamsArray = restTemplate.getForObject("http://localhost:8082/api/teams", SimpleTeamDTO[].class);
        } catch (Exception e) {
            System.err.println("Nie udało się pobrać drużyn: " + e.getMessage());
        }

        List<SimpleTeamDTO> allTeams = teamsArray != null ? List.of(teamsArray) : List.of();

        // 3. Mapowanie w pamięci (Java Stream)
        return leagues.stream()
                .map(league -> {
                    // Filtrujemy drużyny, które należą do tej ligi
                    List<TeamListDTO> leagueTeams = allTeams.stream()
                            .filter(t -> t.leagueId() != null && t.leagueId().equals(league.getId()))
                            .map(t -> new TeamListDTO(t.id(), t.name(), t.rating()))
                            .toList();

                    return new LeagueTeamListDTO(
                            league.getId(),
                            league.getName(),
                            league.getCountry(),
                            leagueTeams
                    );
                })
                .toList();
    }
}