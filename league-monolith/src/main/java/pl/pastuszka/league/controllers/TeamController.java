package pl.pastuszka.league.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pastuszka.league.DTO.Team.TeamCreateDTO;
import pl.pastuszka.league.DTO.Team.TeamListDTO;
import pl.pastuszka.league.DTO.Team.TeamReadDTO;
import pl.pastuszka.league.entity.Team;
import pl.pastuszka.league.repositories.service.LeagueService;
import pl.pastuszka.league.repositories.service.TeamService;

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
        return leagueService.findById(leagueId)
                .map(league ->{
                        List<TeamListDTO> teams = teamService.findByLeagueId(leagueId)
                                .stream()
                                .map(t -> new TeamListDTO(t.getId(), t.getName(), t.getRating()))
                                .toList();
                        return ResponseEntity.ok(teams);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Object> addTeam(@PathVariable UUID leagueId, @RequestBody TeamCreateDTO dto) {
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

    @DeleteMapping("/{teamId}")
    public ResponseEntity<Object> deleteTeam(@PathVariable UUID leagueId, @PathVariable UUID teamId) {

        return teamService.findById(teamId)
                .filter(team -> team.getLeague() != null && team.getLeague().getId().equals(leagueId)) // Walidacja!
                .map(team -> {
                    teamService.deleteById(teamId);
                    return ResponseEntity.noContent().build();
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

    @PutMapping("/{teamId}")
    public ResponseEntity<Object> updateTeam(@PathVariable UUID leagueId, @PathVariable UUID teamId, @RequestBody TeamCreateDTO dto) {
        return teamService.findById(teamId)
                .filter(team -> team.getLeague().getId().equals(leagueId))
                .map(existingTeam -> {
                    existingTeam.setName(dto.name());
                    existingTeam.setCity(dto.city());
                    existingTeam.setRating(dto.rating());

                    teamService.save(existingTeam);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
