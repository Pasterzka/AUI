package pl.pastuszka.league.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


// Lombork
@Getter
@Setter
@Builder
public class League implements Serializable{
    
    // Pola klasy
    private static final long serialVersionUID = 1L;
    private final String name;
    private final String country;
    private final List<Team> teams = new ArrayList<>();

    // Dodanie druzyny do ligi
    public void addTeam(Team team){
        if(team != null) {
            team.setLeague(this);
            teams.add(team);
        }
    }
    // Usuniecie druzyny z ligi
    void removeTeam(Team team){
        teams.remove(team);
    }

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
    public int hashCode() {
        return Objects.hash(name, country, teams);
    }

    @Override
    public String toString() {
        return "Liga [nazwa=" + name + ", kraj=" + country + ", liczba zespolow=" + teams.size()  + "]";
    }

    public String getName() {
        return name;
    }
}
