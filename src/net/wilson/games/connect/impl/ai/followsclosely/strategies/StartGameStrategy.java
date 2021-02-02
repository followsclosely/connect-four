package net.wilson.games.connect.impl.ai.followsclosely.strategies;

import net.wilson.games.connect.impl.MutableBoard;
import net.wilson.games.connect.impl.ai.followsclosely.StinkAI;

/**
 * This strategy will start by placing pieces in the center of the board.
 * Alone wins around 57% of games against a random AI.
 */
public class StartGameStrategy implements StinkAI.Strategy {

    @Override
    public int yourTurn(MutableBoard board) {
        if (board.getTurns().size() < 4){
            return board.getWidth() / 2;
        } else {
            return -1;
        }
    }
}