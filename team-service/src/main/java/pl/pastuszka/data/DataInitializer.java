package pl.pastuszka.data;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import pl.pastuszka.entity.League;
import pl.pastuszka.entity.Team;
import pl.pastuszka.repository.LeagueRepository;
import pl.pastuszka.repository.TeamRepository;

import java.util.UUID;

@Component
public class DataInitializer {
    private final TeamRepository teamRepository;
    private final LeagueRepository leagueRepository;

    public DataInitializer(TeamRepository teamRepository, LeagueRepository leagueRepository) {
        this.teamRepository = teamRepository;
        this.leagueRepository = leagueRepository;
    }

    @PostConstruct
    public void initData() {
        // UUID musi być takie samo jak w LeagueService, żeby testy przeszły łatwo!
        UUID leagueId = UUID.fromString("6a9a2c7e-1234-4abc-bbbb-2eaa3ad122ca");

        // Tworzymy lokalną kopię ligi (bez tego nie dodasz drużyny)
        League plk = League.builder()
                .id(leagueId)
                .name("Orlen Basket Liga")
                .build();
        leagueRepository.save(plk);

        // Tworzymy drużynę
        Team trefl = Team.builder()
                .id(UUID.randomUUID())
                .name("Trefl")
                .city("ttt")
                .rating(85)
                .league(plk)
                .build();
        teamRepository.save(trefl);

        System.out.println("Team-Service: Dane startowe załadowane.");
    }
}
