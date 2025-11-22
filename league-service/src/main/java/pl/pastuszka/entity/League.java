package pl.pastuszka.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import lombok.*;

@Entity
@Table(name = "leagues")
@Getter
@Setter
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class League implements Serializable{
    
    // ID - JPA
    @Id
    @Column(name="id", unique = true, nullable = false)
    private UUID id;

    // Serializacja danych -> Lab1 -> w sumie można usunąć
    //private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    private final String name;

    @Column(nullable = false)
    private final String country;

    //@OneToMany(mappedBy = "league", fetch = FetchType.LAZY,  cascade = CascadeType.REMOVE)
    //private final List<Team> teams = new ArrayList<>();

    // Dodanie druzyny do ligi
    //public void addTeam(Team team){
    //    if(team != null) {
    //        team.setLeague(this);
    //        teams.add(team);
    //    }
    //}
    // Usuniecie druzyny z ligi
    //void removeTeam(Team team){
    //    teams.remove(team);
    //}

    // Metoda equals, hashCode i toString
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        League other = (League) obj;
        return Objects.equals(name, other.name) && Objects.equals(country, other.country);
    }

    @Override
    public String toString() {
        return "Liga [ UUID=" + id + ", nazwa=" + name + ", kraj=" + country + "]";
    }
}
