package pl.pastuszka.entity;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name="teams")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Team implements Serializable, Comparable<Team>{

    @Id
    @Column(name="id",  unique = true, nullable = false)
    private UUID id;

    // Serializacja
    //private static final long serialVersionUID = 1L;

    @Column(name = "city",  nullable = false)
    private  String city;

    @Column(name = "name", nullable = false)
    private  String name;

    @Column(name = "rating")
    private int rating;

    @ManyToOne
    @JoinColumn(name = "league_id")
    private League league;




    @Override
    public int compareTo(Team other) {
        return Integer.compare(this.rating, other.rating);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Team other = (Team) obj;
        String thisLeagueName = league == null ? "" : league.getName();
        String otherLeagueName = other.league == null ? "" : other.league.getName();
        return Objects.equals(name, other.name) &&
                Objects.equals(city, other.city) &&
                Objects.equals(thisLeagueName, otherLeagueName);
    }

    @Override
    public int hashCode() {
        String leagueName = league == null ? "" : league.getName();
        return name.hashCode() + city.hashCode() + leagueName.hashCode();
    }

    @Override
    public String toString() {
        return "Druzyna [ UUID=" + id + ", nazwa=" + name + ", miasto=" + city + ", liga=" + (league != null ? league.getName() : "") + ", ocena=" + rating + "]";
    }

}
