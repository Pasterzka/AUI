package pl.pastuszka.league.controllers;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pastuszka.league.DTO.League.LeagueCreateDTO;
import pl.pastuszka.league.DTO.League.LeagueListDTO;
import pl.pastuszka.league.DTO.League.LeagueReadDTO;
import pl.pastuszka.league.DTO.League.LeagueTeamListDTO;
import pl.pastuszka.league.DTO.Team.TeamListDTO;
import pl.pastuszka.league.entity.League;
import pl.pastuszka.league.repositories.service.LeagueService;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static java.util.Arrays.stream;

@RestController
@RequestMapping("/leagues")
public class LeagueController {

    private final LeagueService leagueService;

    LeagueController(LeagueService leagueService) {
        this.leagueService = leagueService;
    }

    @GetMapping
    public List<LeagueListDTO> getAllLeagues() {
        return leagueService.findAll()
                .stream()
                .map(l -> new LeagueListDTO(l.getId(), l.getName()))
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeagueReadDTO> getLeague(@PathVariable UUID id) {
        return leagueService.findById(id)
                .map(l -> ResponseEntity.ok(new LeagueReadDTO(l.getId(), l.getName(), l.getCountry())))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Object> createLeague(@RequestBody LeagueCreateDTO leagueCreateDTO) {
        League league = League.builder()
                .id(UUID.randomUUID())
                .name(leagueCreateDTO.name())
                .country(leagueCreateDTO.country())
                .build();
        leagueService.save(league);
        return ResponseEntity.created(URI.create("/leagues/" + league.getId())).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeague(@PathVariable UUID id) {
        if (leagueService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        leagueService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/with-teams")
    public List<LeagueTeamListDTO> getAllLeaguesWithTeams() {
        return leagueService.getAllLeaguesWithTeams()
                .stream()
                .map(league -> {
                    // Mapowanie listy Team na listę TeamListDTO
                    List<TeamListDTO> teamsDto = league.getTeams().stream()
                            // Używamy zaktualizowanego TeamListDTO(id, name, rating)
                            .map(t -> new TeamListDTO(t.getId(), t.getName(), t.getRating()))
                            .toList();

                    // Mapowanie League na LeagueWithTeamsDTO
                    return new LeagueTeamListDTO(
                            league.getId(),
                            league.getName(),
                            league.getCountry(),
                            teamsDto
                    );
                })
                .toList();
    }
}
