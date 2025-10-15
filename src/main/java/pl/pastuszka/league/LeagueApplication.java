package pl.pastuszka.league;

import java.util.List;
import java.util.concurrent.ForkJoinPool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import pl.pastuszka.league.DTO.TeamDTO;
import pl.pastuszka.league.entity.League;
import pl.pastuszka.league.entity.Team;
import pl.pastuszka.league.utils.SerializeLeague;
import pl.pastuszka.league.data.LeagueGenerator;

@SpringBootApplication
public class LeagueApplication {

	public static void main(String[] args) {
		//SpringApplication.run(LeagueApplication.class, args);
		
		List<League> leagues = LeagueGenerator.generateLeagues();

		System.err.println("=== Zadanie 2 ===");
		leagues.forEach(league -> {
			System.out.println(league);
			league.getTeams().forEach(team -> System.out.println("  " + team));
		});


		System.out.println("=== Zadanie 3 ===");
		List<Team> allTeams = leagues.stream()
			.flatMap(league -> league.getTeams().stream())
			.distinct()
			.sorted()
			.toList();
			
		
		allTeams.forEach(System.out::println);


		System.err.println("=== Zadanie 4 ===");
		leagues.stream()
			.flatMap(league -> league.getTeams().stream())
			.filter(team -> team.getRating() > 50)
			.sorted()
			.forEach(System.out::println);

		System.err.println("=== Zadanie 5 ===");
		List<TeamDTO> teamDTOs = leagues.stream()
			.flatMap(league -> league.getTeams().stream())
			.distinct()
			.map(team -> TeamDTO.builder()
				.name(team.getName())
				.city(team.getCity())
				.rating(team.getRating())
				.leagueName(team.getLeague() == null ? "" : team.getLeague().getName())
				.build()
			)
			.sorted()
			.toList();

		teamDTOs.forEach(System.out::println);


		System.err.println("=== Zadanie 6 ===");
		leagues = SerializeLeague.task6(leagues, "leagues.json");


		System.out.println("=== Zadanie 7 ===");

		ForkJoinPool customThreadPool = new ForkJoinPool(4);

		final List<League> finalLeagues = leagues; 

		try {
			customThreadPool.submit(() ->{
				finalLeagues.parallelStream().forEach(league -> {
    				System.out.println("\n---" + Thread.currentThread().getName() + " Liga: " + league.getName() + " ---");

    				league.getTeams().forEach(team -> {
        				System.out.println("--- " + Thread.currentThread().getName() + " Druzyna: " + team.toString() + " ---");
    				});

    				//System.out.println("---" + Thread.currentThread().getName() + " Koniec ligi: " + league.getName() + " ---\n");
				});
			}).get();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Zamknięcie puli wątków
			customThreadPool.shutdown();
			try {
				if (!customThreadPool.awaitTermination(60, java.util.concurrent.TimeUnit.SECONDS)) {
					customThreadPool.shutdownNow();
				}
			} catch (InterruptedException e) {
				customThreadPool.shutdownNow();
			}
		}
	}

}
