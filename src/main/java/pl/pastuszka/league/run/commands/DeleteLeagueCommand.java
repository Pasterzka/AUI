package pl.pastuszka.league.run.commands;

import org.springframework.stereotype.Component;
import pl.pastuszka.league.run.CommandHandler;
import pl.pastuszka.league.repositories.service.LeagueService;

import java.util.Scanner;
import java.util.UUID;

@Component
public class DeleteLeagueCommand implements CommandHandler {

    private final LeagueService leagueService;

    public DeleteLeagueCommand(LeagueService leagueService) {
        this.leagueService = leagueService;
    }

    @Override
    public String getName() {
        return "delete_league";
    }

    @Override
    public String getDescription() {
        return " -> Usuń ligę ";
    }

    @Override
    public void execute(Scanner scanner) {

        System.out.print("Podaj UUID ligi do usunięcia: ");
        UUID id = UUID.fromString(scanner.nextLine());

        leagueService.deleteById(id);

        System.out.println("Liga o id - " + id + ", zotsała usunięta");
    }
}
