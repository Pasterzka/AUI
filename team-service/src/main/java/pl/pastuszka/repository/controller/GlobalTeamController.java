package pl.pastuszka.repository.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pastuszka.repository.dto.TeamReadDTO;
import pl.pastuszka.repository.service.TeamService;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class GlobalTeamController {
    private final TeamService teamService;

    public GlobalTeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public List<TeamReadDTO> getAllTeams() {
        return teamService.findAll().stream()
                .map(t -> new TeamReadDTO(
                        t.getId(),
                        t.getName(),
                        t.getCity(),
                        t.getRating(),
                        t.getLeague() != null ? t.getLeague().getId() : null
                ))
                .toList();
    }
}
