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
import pl.pastuszka.league.utils.ThreadWriting;

@SpringBootApplication
public class LeagueApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeagueApplication.class, args);
		
    }

}
