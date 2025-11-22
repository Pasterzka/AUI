package pl.pastuszka.repository.dto;

import java.util.UUID;

public record SimpleTeamDTO(UUID id, String name, String city, int rating, UUID leagueId) {
}
