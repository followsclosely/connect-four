package net.wilson.games.connect;

/**
 *
 */
public interface ArtificialIntelligence {

    /** This method is called by the Engine when it is "your" turn to play.
     *
     * @param board The current state of the game.
     * @return The column (y) to drop the piece.
     */
    int yourTurn(Board board);
}