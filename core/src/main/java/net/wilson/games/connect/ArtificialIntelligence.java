package net.wilson.games.connect;

/**
 * This abstract class can be extended to create your own AI.
 *
 * @see net.wilson.games.connect.impl.ai.Dummy
 */
public abstract class ArtificialIntelligence {

    private int color;

    public ArtificialIntelligence(int color) {
        this.color = color;
    }

    /**
     * Gets the color that the AI is playing for.
     *
     * @return color of the AI player
     */
    public int getColor() {
        return color;
    }

    /**
     * This method is called by the Engine when it is "your" turn to play.
     * It should return the column to drop the piece down.
     *
     * @param board The current state of the game.
     * @return The column (x) to drop the piece.
     */
    abstract public int yourTurn(Board board);
}
