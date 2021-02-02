package net.wilson.games.connect.impl.ai.followsclosely.strategies;

import net.wilson.games.connect.Coordinate;
import net.wilson.games.connect.impl.MutableBoard;
import net.wilson.games.connect.impl.ai.followsclosely.StinkAI;

/**
 * This strategy will check each spot to see if a win can be made.
 * Alone wins around 66% of games against a random AI.
 */
public class BlockWinStrategy implements StinkAI.Strategy {

    @Override
    public int yourTurn(MutableBoard board) {
        Coordinate lastTurn = board.getTurns().isEmpty() ? null : board.getTurns().get(board.getTurns().size()-1);
        if( lastTurn != null) {
            int color = board.getPiece(lastTurn.getX(), lastTurn.getY());
            for (int x = 0, width = board.getWidth(); x < width; x++) {
                int y = board.dropPiece(x, color);
                if (!board.getWinningConnections().isEmpty()) {
                    return x;
                } else {
                    board.undo();
                }
            }
        }
        return -1;
    }
}