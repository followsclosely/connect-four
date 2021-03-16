package io.github.followsclosley.connect;

import io.github.followsclosley.connect.impl.ai.Dummy;

/**
 * This interface is all you need to create your own AI.
 *
 * @see Dummy
 */
public interface ArtificialIntelligence {

    /** This method is called before a game starts.
     *
     * @param opponent Color of the opponent.
     */
    default void initialize(int opponent){}

    /**
     * Gets the color that the AI is playing for.
     *
     * @return color of the AI player
     */
    int getColor();

    /**
     * This method is called by the Engine when it is "your" turn to play.
     * It should return the column to drop the piece down.
     *
     * @param board The current state of the game.
     * @return The column (x) to drop the piece.
     */
     int yourTurn(Board board);
}
