package pl.pastuszka.league.run.commands;

import org.springframework.stereotype.Component;
import pl.pastuszka.league.entity.League;
import pl.pastuszka.league.entity.Team;
import pl.pastuszka.league.run.CommandHandler;
import pl.pastuszka.league.repositories.service.LeagueService;

import java.util.List;
import java.util.Scanner;

@Component
public class ShowTeamCommand implements CommandHandler {

    LeagueService leagueService;

    public ShowTeamCommand(LeagueService leagueService) {
        this.leagueService = leagueService;
    }

    @Override
    public String getName() {
        return "show_teams";
    }

    @Override
    public String getDescription() {
        return " -> Pokaź wszystkie drużyny ";
    }

    @Override
    public void execute(Scanner scanner) {

        List<League> leagues = leagueService.getAllLeaguesWithTeams();

        if (leagues.isEmpty()) {
            System.out.println("Brak lig w bazie danych!");
            return;
        }

        System.out.println("=== Drużyny w ligach ===");

        int i = 1;
        for (League league : leagues) {
            System.out.println(i++ + ". " + league.toString());

            int j = 1;
            for(Team team : league.getTeams()) {
                System.out.println("    " + j + ". " + team.toString());
                j++;
            }

            System.out.println();
            i++;
        }
    }
}
