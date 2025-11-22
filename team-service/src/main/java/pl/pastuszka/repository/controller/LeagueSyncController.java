package pl.pastuszka.repository.controller;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.pastuszka.entity.League;
import pl.pastuszka.entity.Team;
import pl.pastuszka.repository.LeagueRepository;
import pl.pastuszka.repository.TeamRepository;
import pl.pastuszka.repository.dto.LeagueEventDTO;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/sync/leagues")
public class LeagueSyncController {

    private final LeagueRepository leagueRepository;
    private final TeamRepository teamRepository;

    public LeagueSyncController(LeagueRepository leagueRepository, TeamRepository teamRepository) {
        this.leagueRepository = leagueRepository;
        this.teamRepository = teamRepository;
    }

    @PutMapping("/{id}")
    public void syncLeague(@PathVariable UUID id, @RequestBody LeagueEventDTO dto) {
        // Aktualizujemy lub tworzymy uproszczoną ligę
        League league = new League(id, dto.name());
        leagueRepository.save(league);
        System.out.println("Team-Service: Zsynchronizowano ligę " + dto.name());
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void deleteLeague(@PathVariable UUID id) {
        // Najpierw usuwamy drużyny tej ligi
        List<Team> teams = teamRepository.findByLeagueId(id);
        teamRepository.deleteAll(teams);

        // Potem usuwamy samą ligę
        leagueRepository.deleteById(id);
        System.out.println("Team-Service: Usunięto ligę i jej drużyny: " + id);
    }
}
