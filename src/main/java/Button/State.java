package Button;

/**
 * Enum to make a difference between between buttons. In minesweeper, 3 kind of buttons can appear, this enum defines them.
 */
public enum State{
    /**
     * Bomb type tile.
     */
    BOMB,
    /**
     * Empty type tile.
     */
    EMPTY,
    /**
     * Number type tile.
     */
    NUMBER
}
