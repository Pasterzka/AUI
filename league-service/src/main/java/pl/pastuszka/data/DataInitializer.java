package pl.pastuszka.data;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import pl.pastuszka.entity.League;
import pl.pastuszka.repository.service.LeagueService;

import java.util.UUID;

@Component
public class DataInitializer {

    private final LeagueService leagueService;

    public DataInitializer(LeagueService leagueService) {
        this.leagueService = leagueService;
    }

    @PostConstruct
    public void initData() {
        League plk = League.builder()
                .id(UUID.fromString("6a9a2c7e-1234-4abc-bbbb-2eaa3ad122ca"))
                .name("Orlen Basket Liga")
                .country("Polska")
                .build();

        try {
            leagueService.save(plk);
            System.out.println("League-Service: Dodano ligę.");
        } catch (Exception e) {
            System.out.println("League-Service: Liga dodana lokalnie, ale sync nieudany (to normalne przy starcie): " + e.getMessage());
        }
    }
}
