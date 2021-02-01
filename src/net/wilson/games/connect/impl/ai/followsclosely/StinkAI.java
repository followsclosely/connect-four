package net.wilson.games.connect.impl.ai.followsclosely;

import net.wilson.games.connect.ArtificialIntelligence;
import net.wilson.games.connect.Board;
import net.wilson.games.connect.impl.MutableBoard;
import net.wilson.games.connect.impl.ai.followsclosely.strategies.RandomStrategy;
import net.wilson.games.connect.impl.ai.followsclosely.strategies.WinIfICanStrategy;

import java.util.ArrayList;
import java.util.List;

public class StinkAI extends ArtificialIntelligence {

    List<Strategy> chain = new ArrayList<>();
    Strategy defaultStrategy = new RandomStrategy();

    public StinkAI(int color) {
        super(color);
        chain.add(new WinIfICanStrategy(getColor()));
    }

    @Override
    public int yourTurn(Board b) {
        MutableBoard board = new MutableBoard(b);
        for(Strategy strategy : chain){
            int x = strategy.yourTurn(board);
            if( x != -1 ){
                return x;
            }
        }

        //Return a random selection if none has yet been made.
        return defaultStrategy.yourTurn(board);
    }
}