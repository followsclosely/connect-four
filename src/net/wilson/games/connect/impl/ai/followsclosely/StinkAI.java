package net.wilson.games.connect.impl.ai.followsclosely;

import net.wilson.games.connect.ArtificialIntelligence;
import net.wilson.games.connect.Board;
import net.wilson.games.connect.impl.MutableBoard;
import net.wilson.games.connect.impl.ai.followsclosely.strategies.RandomStrategy;
import net.wilson.games.connect.impl.ai.followsclosely.strategies.WinIfICanStrategy;

import java.util.ArrayList;
import java.util.List;

public class StinkAI extends ArtificialIntelligence {

    private List<Strategy> chain = new ArrayList<>();
    private Strategy defaultStrategy = new RandomStrategy();

    public StinkAI(int color) {
        super(color);
        chain.add(new WinIfICanStrategy(getColor()));
        chain.add(new RandomStrategy());
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

        //Should never happen as the last strategy will always return a valid x.
        return -1;
    }
}