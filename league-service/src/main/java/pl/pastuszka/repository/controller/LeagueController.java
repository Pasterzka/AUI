package pl.pastuszka.repository.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pastuszka.entity.League;
import pl.pastuszka.repository.dto.LeagueCreateDTO;
import pl.pastuszka.repository.dto.LeagueListDTO;
import pl.pastuszka.repository.dto.LeagueReadDTO;
import pl.pastuszka.repository.dto.LeagueTeamListDTO;
import pl.pastuszka.repository.service.LeagueService;


import java.net.URI;
import java.util.List;
import java.util.UUID;

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
        leagueService.save(league); // To wywoła synchronizację w serwisie
        return ResponseEntity.created(URI.create("/leagues/" + league.getId())).build();
    }

    // Metoda PUT (Aktualizacja lub tworzenie ze znanym ID)
    @PutMapping("/{id}")
    public ResponseEntity<Object> createOrUpdateLeagueWithId(@PathVariable UUID id,
                                                             @RequestBody LeagueCreateDTO dto) {
        // Logika uproszczona - po prostu budujemy obiekt i zapisujemy
        // Service sprawdzi czy to update czy save, ale w obu przypadkach wyśle sync do team-service
        League league = League.builder()
                .id(id)
                .name(dto.name())
                .country(dto.country())
                .build();

        leagueService.save(league);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeague(@PathVariable UUID id) {
        if (leagueService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        leagueService.deleteById(id); // To wywoła synchronizację (delete)
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/with-teams")
    public List<LeagueTeamListDTO> getLeaguesWithTeams() {
        return leagueService.getAllLeaguesWithTeams();
    }
}