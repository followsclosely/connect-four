package net.wilson.games.connect.impl.ai.followsclosely.strategies;

import net.wilson.games.connect.impl.MutableBoard;
import net.wilson.games.connect.impl.ai.followsclosely.StinkAI;

public class StartGameStrategy implements StinkAI.Strategy {

    @Override
    public int yourTurn(MutableBoard board) {
        if (board.getTurns().size() < 5){
            return board.getWidth() / 2;
        } else {
            return -1;
        }
    }
}