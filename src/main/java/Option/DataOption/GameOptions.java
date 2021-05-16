package Option.DataOption;

import lombok.Data;

/**
 * This class contains the imported options related to the game.
 */
@Data
public class GameOptions implements Option{

    private String nickName;
    private Integer difficulty;

}
