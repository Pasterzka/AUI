package pl.pastuszka.repository.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
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
    private final RestTemplate restTemplate;

    // Adresy do komunikacji z Team-Service (Port 8082)
    // Używamy localhost, bo serwisy widzą się nawzajem
    private final String TEAM_SERVICE_SYNC_URL = "http://localhost:8082/api/sync/leagues";
    private final String TEAM_SERVICE_GET_ALL_URL = "http://localhost:8082/api/teams";

    public LeagueService(LeagueRepository leagueRepository, RestTemplate restTemplate) {
        this.leagueRepository = leagueRepository;
        this.restTemplate = restTemplate;
    }

    public List<League> findAll() {
        return leagueRepository.findAll();
    }

    public Optional<League> findById(UUID id) {
        return leagueRepository.findById(id);
    }

    public void save(League league) {
        leagueRepository.save(league);

        // SYNC: Powiadom team-service o nowej/zmienionej lidze
        try {
            restTemplate.put(TEAM_SERVICE_SYNC_URL + "/" + league.getId(),
                    new LeagueEventDTO(league.getId(), league.getName()));
        } catch (Exception e) {
            System.err.println("Błąd synchronizacji (PUT): " + e.getMessage());
        }
    }

    public void deleteById(UUID id) {
        if (leagueRepository.existsById(id)) {
            leagueRepository.deleteById(id);

            // SYNC: Powiadom team-service o usunięciu
            try {
                restTemplate.delete(TEAM_SERVICE_SYNC_URL + "/" + id);
            } catch (Exception e) {
                System.err.println("Błąd synchronizacji (DELETE): " + e.getMessage());
            }
        }
    }


    public List<LeagueTeamListDTO> getAllLeaguesWithTeams() {
        // Ligi z bazy H2 (League DB)
        List<League> leagues = leagueRepository.findAll();

        // Drużyny z API (Team Service)
        SimpleTeamDTO[] teamsArray = {};
        try {
            teamsArray = restTemplate.getForObject(TEAM_SERVICE_GET_ALL_URL, SimpleTeamDTO[].class);
        } catch (Exception e) {
            System.err.println("Nie udało się pobrać listy drużyn: " + e.getMessage());
        }

        // Zabezpieczenie na wypadek null (gdy team-service leży)
        List<SimpleTeamDTO> allTeams = teamsArray != null ? List.of(teamsArray) : List.of();

        // 3. Mapowanie i łączenie
        return leagues.stream().map(league -> {

            // Filtrujemy drużyny należące do tej konkretnej ligi
            List<TeamListDTO> leagueTeams = allTeams.stream()
                    .filter(t -> t.leagueId() != null && t.leagueId().equals(league.getId()))
                    .map(t -> new TeamListDTO(t.id(), t.name(), t.city(), t.rating()))
                    .toList();

            // Tworzymy obiekt wynikowy
            return new LeagueTeamListDTO(
                    league.getId(),
                    league.getName(),
                    league.getCountry(),
                    leagueTeams
            );
        }).toList();
    }
}