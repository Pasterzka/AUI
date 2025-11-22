package pl.pastuszka.entity;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name="leagues")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class League {

    @Id
    @Column(name="id", unique = true, nullable = false)
    private UUID id;

    private String name;
}
