package pl.pastuszka.league;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import pl.pastuszka.league.DTO.TeamDTO;
import pl.pastuszka.league.data.LeagueGenerator;
import pl.pastuszka.league.entity.League;

@SpringBootApplication
public class LeagueApplication {

	public static void main(String[] args) {
		//SpringApplication.run(LeagueApplication.class, args);
		
		List<League> leagues = LeagueGenerator.generateLeagues();

		List<TeamDTO> teamDTOs = leagues.stream()
			.flatMap(league -> league.getTeams().stream())
			.map(team -> TeamDTO.builder()
				.name(team.getName())
				.city(team.getCity())
				.rating(team.getRating())
				.leagueName(team.getLeague() == null ? "" : team.getLeague().getName())
				.build()
			)
			.sorted()
			.toList();
			
		System.out.println("=== Drużyny DTO ===");
		teamDTOs.forEach(System.out::println);
		System.out.println("===================");
	}

}
