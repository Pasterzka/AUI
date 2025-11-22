package pl.pastuszka.league.run.commands;

import org.springframework.stereotype.Component;
import pl.pastuszka.league.entity.League;
import pl.pastuszka.league.entity.Team;
import pl.pastuszka.league.run.CommandHandler;
import pl.pastuszka.league.repositories.service.LeagueService;
import pl.pastuszka.league.repositories.service.TeamService;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

@Component
public class AddTeamCommand implements CommandHandler {

    private final LeagueService leagueService;
    private final TeamService teamService;

    public AddTeamCommand(LeagueService leagueService,  TeamService teamService) {
        this.leagueService = leagueService;
        this.teamService = teamService;
    }

    @Override
    public String getName() {
        return "add_team";
    }

    @Override
    public String getDescription() {
        return " -> Dodaj nową drużynę ";
    }

    @Override
    public void execute(Scanner scanner) {
        System.out.print("Podaj nazwę drużyny: ");
        String name = scanner.nextLine();
        System.out.print("Podaj miasto: ");
        String city = scanner.nextLine();
        System.out.print("Podaj ocenę (rating): ");
        int rating = Integer.parseInt(scanner.nextLine());

        List<League> leagues = leagueService.findAll();
        if (leagues.isEmpty()) {
            System.out.println(" === Brak lig! Dodaj najpierw ligę. === ");
            return;
        }

        System.out.println("Wybierz ligę:");
        for (int i = 0; i < leagues.size(); i++) {
            System.out.println((i + 1) + ". " + leagues.get(i).toString());
        }

        int index = Integer.parseInt(scanner.nextLine()) - 1;
        League selected = leagues.get(index);

        Team team = Team.builder()
                .id(UUID.randomUUID())
                .name(name)
                .city(city)
                .rating(rating)
                .league(selected)
                .build();

        teamService.save(team);

        System.out.println(" === Dodano drużynę dp " + selected.toString() + ". === ");


    }
}
