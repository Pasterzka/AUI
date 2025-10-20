package pl.pastuszka.league.run;

import java.util.Scanner;

public interface CommandHandler {

    String getName();
    String getDescription();
    void execute(Scanner scanner);

}
