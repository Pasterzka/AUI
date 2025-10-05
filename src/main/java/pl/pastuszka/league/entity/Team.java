package pl.pastuszka.league.entity;

import java.io.Serializable;
import java.util.Objects;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Team implements Serializable,Comparable<Team> {
    
   private static final long serialVersionUID = 1L;
   private final String name;
   private final String city;
   private League league;
   private int rating;


    @Override
    public int compareTo(Team other) {
        return this.name.compareTo(other.name);
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
        return "Druzyna [nazwa=" + name + ", miasto=" + city + ", liga=" + (league != null ? league.getName() : "") + "]";
    }

    void setLeague(League league) {
        this.league = league;
    }
}