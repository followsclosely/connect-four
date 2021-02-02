package net.wilson.games.connect.impl.ai.followsclosely;

import net.wilson.games.connect.ArtificialIntelligence;
import net.wilson.games.connect.Board;
import net.wilson.games.connect.impl.MutableBoard;
import net.wilson.games.connect.impl.ai.followsclosely.strategies.BlockWinStrategy;
import net.wilson.games.connect.impl.ai.followsclosely.strategies.StartGameStrategy;
import net.wilson.games.connect.impl.ai.followsclosely.strategies.WinIfICanStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This implementation of ArtificialIntelligence uses the chain of responsibility
 * pattern to decide which is hte best place to drop the piece.
 *
 * It currently wins 88% against a random ArtificialIntelligence.
 *
 * @see WinIfICanStrategy
 * @see BlockWinStrategy
 * @see StartGameStrategy
 */
public class StinkAI extends ArtificialIntelligence {

    private List<Strategy> chain = new ArrayList<>();

    public StinkAI(int color) {
        super(color);
        chain.add(new WinIfICanStrategy(getColor()));
        chain.add(new BlockWinStrategy());
        chain.add(new StartGameStrategy());
        chain.add(this::randomStrategy);
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

    public interface Strategy {
        int yourTurn(MutableBoard board);
    }

    /**
     * This Strategy will always return a square at random.
     */
    private Random random = new Random();
    public int randomStrategy(MutableBoard board) {
        MutableBoard myBoard = new MutableBoard(board);

        //Select a random place on the board
        int x = random.nextInt(board.getWidth());

        //Keep adding one to the random spot while canDropPiece is false or we get to the width of the board
        for (int i = 0, width = board.getWidth(); i < width && !myBoard.canDropPiece(x); i++, x = (x + 1) % board.getWidth());

        return x;
    }
}