package pl.pastuszka.repository.dto;

import java.util.List;
import java.util.UUID;

// Rekord, który zawiera dane ligi ORAZ listę drużyn
public record LeagueTeamListDTO(
        UUID id,
        String name,
        String country,
        List<TeamListDTO> teams
) {}