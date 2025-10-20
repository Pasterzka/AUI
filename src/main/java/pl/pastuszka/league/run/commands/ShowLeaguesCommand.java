package pl.pastuszka.league.run.commands;

import org.springframework.stereotype.Component;
import pl.pastuszka.league.entity.League;
import pl.pastuszka.league.run.CommandHandler;
import pl.pastuszka.league.repositories.service.LeagueService;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ShowLeaguesCommand implements CommandHandler {

    private final LeagueService leagueService;

    public ShowLeaguesCommand(LeagueService leagueService) {
        this.leagueService = leagueService;
    }

    @Override
    public String getName() {
        return "show_leagues";
    }

    @Override
    public String getDescription() {
        return " -> Pokaż wszystkie ligi ";
    }

    @Override
    public void execute(Scanner scanner) {
        List<League> leagues = leagueService.findAll();

        System.out.println(" === Wszystkie Ligi: === ");

        AtomicInteger i= new AtomicInteger(1);
        leagues.forEach(l -> {

            System.out.println(i + ". " +l.toString());
            i.getAndIncrement();
        });
    }
}
