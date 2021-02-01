package net.wilson.games.connect.impl.ai.followsclosely.strategies;

import net.wilson.games.connect.impl.MutableBoard;
import net.wilson.games.connect.impl.ai.followsclosely.StinkAI;

public class WinIfICanStrategy implements StinkAI.Strategy {

    private int color;

    public WinIfICanStrategy(int color) {
        this.color = color;
    }

    @Override
    public int yourTurn(MutableBoard board) {
        for(int x=0, width = board.getWidth(); x<width; x++){
            int y = board.dropPiece(x, color);
            if( !board.getWinningConnections().isEmpty()){
                return x;
            } else  {
                board.undo();
            }
        }
        return -1;
    }
}