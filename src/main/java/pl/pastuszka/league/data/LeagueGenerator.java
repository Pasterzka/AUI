package pl.pastuszka.league.data;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import pl.pastuszka.league.entity.League;
import pl.pastuszka.league.entity.Team;

public class LeagueGenerator {
    private static final List<String> LEAGUE_NAMES = Arrays.asList("Orlen Basket Liga", "NBA", "EuroLeague", "Liga ACB");
    private static final List<String> COUNTRIES = Arrays.asList("Polska", "USA", "Hiszpania", "Niemcy", "Wlochy");
    private static final List<String> TEAM_NAMES = Arrays.asList("Legia", "Lakers", "Trefl", "Heat", "Celtics", "Arka", "Czarni", "Bayern", "Juventus", "Real", "Barcelona", "GTK", "Milan", "Inter", "Raptors", "Knicks", "Spurs", "Nets", "Bulls", "Warriors");
    private static final List<String> CITIES = Arrays.asList("Warszawa", "Los Angeles", "Sopot", "Miami", "Boston", "Gdynia", "Słupsk", "Monachium", "Turyn", "Madryt", "Barcelona", "Wroclaw", "Mediolan", "Nowy Jork", "Toronto", "Chicago", "San Francisco");


    public static List<League> generateLeagues() {
        Random random = new Random();
        List<League> leagues = new ArrayList<>();

        for (String leagueName : LEAGUE_NAMES) {
            League league = League.builder()
                .name(leagueName)
                .country(COUNTRIES.get(random.nextInt(COUNTRIES.size())))
                .build();

            // Generowanie druzyn   
            for (int i = 0; i < 20; i++) {
                
                String teamName = TEAM_NAMES.get(random.nextInt(TEAM_NAMES.size()));
                String city = CITIES.get(random.nextInt(CITIES.size()));
                int rating = random.nextInt(101); // Ocena od 0 do 100

                Team team = Team.builder()
                    .name(teamName)
                    .city(city)
                    .rating(rating)
                    .build();
                
                team.setLeague(league);
                league.addTeam(team);
                
            }

            leagues.add(league);
        }

        return leagues;
    }
}
