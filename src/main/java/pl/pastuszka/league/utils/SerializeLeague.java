package pl.pastuszka.league.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Scanner;

import pl.pastuszka.league.entity.League;

public class SerializeLeague {
    public static List<League> task6(List<League> leagues, String filePath) {
        // Wybór serializacja czy deserializacja

        Scanner scanner = new Scanner(System.in);
        System.out.println("Wybierz opcję: 1 - Serializacja, 2 - Deserializacja");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                // Serializacja
                serializeLeagues(leagues, filePath);
                break;
            case 2:
                // Deserializacja
                leagues = deserializeLeagues(filePath);
                break;
            default:
                System.out.println("Nieprawidłowy wybór.");
                break;
        }

        scanner.close();
        return leagues;
    }

    public static void serializeLeagues(List<League> leagues, String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(leagues);
            System.out.println("Ligi zostały zserializowane do pliku: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<League> deserializeLeagues(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {

            List<League> loadedLeagues = (List<League>) ois.readObject();
            System.out.println("Ligi zostały zdeserializowane z pliku: " + filePath);
            loadedLeagues.forEach(league -> {
                System.out.println(league);
                league.getTeams().forEach(team -> System.out.println("  " + team));
            });
            return loadedLeagues;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
