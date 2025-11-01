package pl.pastuszka.league.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
@Builder
public class TeamDTO2 implements Serializable, Comparable<TeamDTO2> {
    
    private String name;
    private String city;
    private String leagueName;
    private int rating;

    @Override
    public String toString() {
        return "TeamDTO [nazwa=" + name + ", miasto=" + city + ", liga=" + leagueName + ", ocena=" + rating + "]";
    }
    
    @Override
    public int compareTo(TeamDTO2 other) {
        return Integer.compare(this.rating, other.rating);
    }
}
