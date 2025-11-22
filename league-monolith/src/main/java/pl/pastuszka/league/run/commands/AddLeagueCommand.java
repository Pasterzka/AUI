package pl.pastuszka.league.run.commands;

import org.springframework.stereotype.Component;
import pl.pastuszka.league.entity.League;
import pl.pastuszka.league.run.CommandHandler;
import pl.pastuszka.league.repositories.service.LeagueService;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

@Component
public class AddLeagueCommand implements CommandHandler {

    private LeagueService leagueService;


    public AddLeagueCommand(LeagueService leagueService) {
        this.leagueService = leagueService;
    }

    @Override
    public String getName() {
        return "add_league";
    }

    @Override
    public String getDescription() {
        return " -> Dodaj nową ligę ";
    }

    @Override
    public void execute(Scanner scanner) {
        System.out.print("Podaj nazwę ligi: ");
        String name = scanner.nextLine();
        System.out.print("Podaj państwo: ");
        String country = scanner.nextLine();

        League league = League.builder()
                .id(UUID.randomUUID())
                .name(name)
                .country(country)
                .build();

        leagueService.save(league);

        System.out.println(" === Dodano Ligę! ===");

        List<League> leagues = leagueService.findAll();

        System.out.println(" === Wszystkie Ligi: === ");
        leagues.forEach(l -> System.out.println(l.toString()));
    }
}
