package pl.pastuszka.league.DTO.League;

import pl.pastuszka.league.DTO.Team.TeamListDTO;
import pl.pastuszka.league.DTO.Team.TeamReadDTO;

import java.util.List;
import java.util.UUID;

public record LeagueTeamListDTO(
   UUID id,
   String name,
   String country,
   List<TeamListDTO> teams
) {}
