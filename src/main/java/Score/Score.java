package Score;

import lombok.Data;

@Data
public class Score implements Comparable<Score>{

    private String name;
    private Double score;
    private Long time;
    private Integer bombNumber;
    private Integer place;

    @Override
    public int compareTo(Score o) {
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
