package pl.pastuszka.repository.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pastuszka.entity.Team;
import pl.pastuszka.repository.service.LeagueService;
import pl.pastuszka.repository.service.TeamService;
import pl.pastuszka.repository.dto.TeamCreateDTO;
import pl.pastuszka.repository.dto.TeamListDTO;
import pl.pastuszka.repository.dto.TeamReadDTO;


import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/leagues/{leagueId}/teams")
public class TeamController {

    private final TeamService teamService;
    private final LeagueService leagueService;

    public TeamController(TeamService teamService, LeagueService leagueService) {
        this.teamService = teamService;
        this.leagueService = leagueService;
    }

    @GetMapping
    public ResponseEntity<List<TeamListDTO>> getTeams(@PathVariable("leagueId") UUID leagueId) {
        // Sprawdzamy, czy mamy taką ligę w lokalnej bazie (zsynchronizowaną)
        return leagueService.findById(leagueId)
                .map(league -> {
                    List<TeamListDTO> teams = teamService.findByLeagueId(leagueId)
                            .stream()
                            .map(t -> new TeamListDTO(t.getId(), t.getName(), t.getRating()))
                            .toList();
                    return ResponseEntity.ok(teams);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{teamId}")
    public ResponseEntity<TeamReadDTO> getTeam(@PathVariable UUID leagueId, @PathVariable UUID teamId) {
        return teamService.findById(teamId)
                .filter(team -> team.getLeague().getId().equals(leagueId))
                .map(team -> ResponseEntity.ok(new TeamReadDTO(
                        team.getId(),
                        team.getName(),
                        team.getCity(),
                        team.getRating(),
                        leagueId)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Object> addTeam(@PathVariable UUID leagueId, @RequestBody TeamCreateDTO dto) {
        // Kluczowe: Możemy dodać drużynę TYLKO do ligi, która istnieje w naszej lokalnej kopii
        return leagueService.findById(leagueId)
                .map(league -> {
                    Team team = Team.builder()
                            .id(UUID.randomUUID())
                            .name(dto.name())
                            .city(dto.city())
                            .rating(dto.rating())
                            .league(league)
                            .build();
                    teamService.save(team);
                    return ResponseEntity.created(URI.create("/leagues/" + leagueId + "/teams/" + team.getId())).build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{teamId}")
    public ResponseEntity<Object> createOrUpdateTeam(@PathVariable UUID leagueId, @PathVariable UUID teamId, @RequestBody TeamCreateDTO dto) {
        return leagueService.findById(leagueId)
                .map(league -> {
                    Team team = teamService.findById(teamId)
                            .orElse(Team.builder().id(teamId).league(league).build());

                    // Aktualizacja pól
                    team.setName(dto.name());
                    team.setCity(dto.city());
                    team.setRating(dto.rating());

                    // Upewnienie się, że liga jest ustawiona (przydatne przy tworzeniu nowego)
                    if (team.getLeague() == null) {
                        team.setLeague(league);
                    }

                    teamService.save(team);

                    boolean isNew = team.getId().equals(teamId) && teamService.findById(teamId).isEmpty();
                    return isNew
                            ? ResponseEntity.created(URI.create("/leagues/" + leagueId + "/teams/" + teamId)).build()
                            : ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<Object> deleteTeam(@PathVariable UUID leagueId, @PathVariable UUID teamId) {
        return teamService.findById(teamId)
                .filter(team -> team.getLeague() != null && team.getLeague().getId().equals(leagueId))
                .map(team -> {
                    teamService.deleteById(teamId);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
