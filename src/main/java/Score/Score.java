package Score;

import lombok.Data;
import lombok.NonNull;

/**
 * This class represents a score object. A score has a player name, a score number, an elapsed time, a number of bombs and
 * a place on the scoreboard.
 */
@Data
public class Score implements Comparable<Score>{

    private String name;
    private Double score;
    private Long time;
    private Integer bombNumber;
    private Integer place;

    @Override
    public int compareTo(@NonNull Score o) {
        if(this.equals(o))
        {
            return 0;
        }else
        {
            return score.compareTo(o.getScore());
        }
    }

    @Override
    public String toString() {
        return "{" +
                "score"  + ':' + score +
                ",bombNumber" + ':' + bombNumber +
                ",name" + ':' + '\"' + name  + '\"' +
                ",time" + ':' + time +
                '}';
    }
}
