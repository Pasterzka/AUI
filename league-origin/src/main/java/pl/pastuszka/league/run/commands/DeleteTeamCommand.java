package pl.pastuszka.league.run.commands;

import org.springframework.stereotype.Component;
import pl.pastuszka.league.run.CommandHandler;
import pl.pastuszka.league.repositories.service.TeamService;

import java.util.Scanner;
import java.util.UUID;

@Component
public class DeleteTeamCommand implements CommandHandler {

    private final TeamService teamService;

    public DeleteTeamCommand(TeamService teamService) {
        this.teamService = teamService;
    }

    @Override
    public String getName() {
        return "delete_team";
    }

    @Override
    public String getDescription() {
        return " -> Usuwanie Drużyny";
    }

    @Override
    public void execute(Scanner scanner) {

        System.out.print("Podaj UUID drużyny do usunięcia: ");
        UUID id = UUID.fromString(scanner.nextLine());

        teamService.deleteById(id);

        System.out.println("Drużyna o id - " + id + ", zotsała usunięta");
    }
}
