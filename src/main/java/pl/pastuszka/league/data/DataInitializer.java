package pl.pastuszka.league.data;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import pl.pastuszka.league.entity.League;
import pl.pastuszka.league.entity.Team;
import pl.pastuszka.league.repositories.LeagueRepository;
import pl.pastuszka.league.repositories.TeamRepository;
import pl.pastuszka.league.service.LeagueService;
import pl.pastuszka.league.service.TeamService;

import java.util.UUID;

@Component
public class DataInitializer {

    private final LeagueService leagueService;
    private final TeamService teamService;


    public DataInitializer(LeagueService leagueService, TeamService teamService) {
        this.leagueService = leagueService;
        this.teamService = teamService;
    }


    @PostConstruct
    public void initData(){
        League plk = League.builder()
                .id(UUID.randomUUID())
                .name("Orlen Basket Liga")
                .country("Polska")
                .build();

        leagueService.save(plk);

        Team trefl = Team.builder()
                .id(UUID.randomUUID())
                .name("Trefl")
                .city("Sopot")
                .rating(50)
                .league(plk)
                .build();

        teamService.save(trefl);

        Team legia = Team.builder()
                .id(UUID.randomUUID())
                .name("Legia")
                .city("Warszawa")
                .rating(55)
                .league(plk)
                .build();

        teamService.save(legia);

        System.out.println("Dodano przykładowe dane");
    }
}
