package pl.pastuszka.league.run;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Component
public class LeagueCommandRunner implements CommandLineRunner {

    private final Map<String, CommandHandler> commandMap = new LinkedHashMap<>();

    public LeagueCommandRunner(List<CommandHandler> commandHandlers) {

        for  (CommandHandler commandHandler : commandHandlers) {
            commandMap.put(commandHandler.getName(), commandHandler);
        }
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.println(" === Menadżer Lig === ");

        boolean exit = false;

        while (!exit) {
            System.out.println(" --- Komendy --- ");

            commandMap.values().forEach(cmd ->
                    System.out.println(" - " + cmd.getName() + " - " + cmd.getDescription())
            );

            System.out.println(" - exit - Zakończ program");
            System.out.println(" - Wpisz komendę: - ");


            String input = scanner.nextLine().trim();


            if (input.equalsIgnoreCase("exit")) {
                exit = true;
            } else if (commandMap.containsKey(input)) {
                commandMap.get(input).execute(scanner);
            } else {
                System.out.println(" === Nieznana komenda: " + input + " === ");
            }

        }

        System.out.println(" === Program zakończony ===");
        System.exit(0);
    }
}
