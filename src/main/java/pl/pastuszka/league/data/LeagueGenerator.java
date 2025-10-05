package pl.pastuszka.league.data;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import pl.pastuszka.league.entity.League;
import pl.pastuszka.league.entity.Team;

public class LeagueGenerator {
    public static List<League> generateLeagues() {
        League pl = League.builder()
            .name("Orlen Basket Liga")
            .country("Polska")
            .build();

        League usa = League.builder()
            .name("NBA")
            .country("USA")
            .build();

        Team t1 = Team.builder()
            .name("Legia")
            .city("Warszawa")
            .rating(60)
            .build();

        Team t2 = Team.builder()
            .name("Lakers")
            .city("Los Angeles")
            .rating(95)
            .build();

        Team t3 = Team.builder()
            .name("Trefl")
            .city("Sopot")
            .rating(55)
            .build();

        Team t4 = Team.builder()
            .name("Heat")
            .city("Miami")
            .rating(90)
            .build();

        pl.addTeam(t1);
        usa.addTeam(t2);
        pl.addTeam(t3);
        usa.addTeam(t4);

        return Arrays.asList(pl, usa);
    }
}
