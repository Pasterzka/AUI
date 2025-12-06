package pl.pastuszka.repository.dto;

import java.util.UUID;

public record TeamListDTO(UUID id, String name, String city, int rating) {
}
