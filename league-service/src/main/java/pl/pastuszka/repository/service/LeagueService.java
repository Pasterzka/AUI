package pl.pastuszka.repository.service;

import java.util.List; // <--- WAŻNY IMPORT
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import pl.pastuszka.entity.League;
import pl.pastuszka.repository.LeagueRepository;
import pl.pastuszka.repository.dto.LeagueEventDTO;
import pl.pastuszka.repository.dto.LeagueTeamListDTO;
import pl.pastuszka.repository.dto.SimpleTeamDTO;
import pl.pastuszka.repository.dto.TeamListDTO;

@Service
public class LeagueService {

    private final LeagueRepository leagueRepository;
    private final RestTemplate restTemplate;

    @Value("${team.service.url:http://localhost:8082}")
    private String teamServiceUrl;

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

        String url = teamServiceUrl + "/api/sync/leagues/" + league.getId();
        
        try {
            restTemplate.put(url, new LeagueEventDTO(league.getId(), league.getName()));
        } catch (Exception e) {
            System.err.println("Błąd synchronizacji (PUT) pod adres: " + url + " -> " + e.getMessage());
        }
    }

    public void deleteById(UUID id) {
        if (leagueRepository.existsById(id)) {
            leagueRepository.deleteById(id);

            String url = teamServiceUrl + "/api/sync/leagues/" + id;

            try {
                restTemplate.delete(url);
            } catch (Exception e) {
                System.err.println("Błąd synchronizacji (DELETE) pod adres: " + url + " -> " + e.getMessage());
            }
        }
    }

    public List<LeagueTeamListDTO> getAllLeaguesWithTeams() {
        List<League> leagues = leagueRepository.findAll();

        SimpleTeamDTO[] teamsArray = {};
        
        
        String url = teamServiceUrl + "/api/teams";

        try {
            teamsArray = restTemplate.getForObject(url, SimpleTeamDTO[].class);
        } catch (Exception e) {
            System.err.println("Nie udało się pobrać listy drużyn z adresu: " + url + " -> " + e.getMessage());
        }

        List<SimpleTeamDTO> allTeams = teamsArray != null ? List.of(teamsArray) : List.of();

        return leagues.stream().map(league -> {
            List<TeamListDTO> leagueTeams = allTeams.stream()
                    .filter(t -> t.leagueId() != null && t.leagueId().equals(league.getId()))
                    .map(t -> new TeamListDTO(t.id(), t.name(), t.city(), t.rating()))
                    .toList();

            return new LeagueTeamListDTO(
                    league.getId(),
                    league.getName(),
                    league.getCountry(),
                    leagueTeams
            );
        }).toList();
    }
}