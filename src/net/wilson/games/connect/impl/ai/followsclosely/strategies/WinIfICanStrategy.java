package net.wilson.games.connect.impl.ai.followsclosely.strategies;

import net.wilson.games.connect.impl.MutableBoard;
import net.wilson.games.connect.impl.ai.followsclosely.Strategy;

public class WinIfICanStrategy implements Strategy {

    private int color;

    public WinIfICanStrategy(int color) {
        this.color = color;
    }

    @Override
    public int yourTurn(MutableBoard board) {
        for(int x=0, width = board.getWidth(); x<width; x++){
            int y = board.dropPiece(x, 0);
            if( !board.getWinningConnections().isEmpty()){
                return x;
            } else  {
                board.undo();
            }
        }
        return -1;
    }
}