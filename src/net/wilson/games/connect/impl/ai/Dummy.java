package net.wilson.games.connect.impl.ai;

import net.wilson.games.connect.ArtificialIntelligence;
import net.wilson.games.connect.Board;
import net.wilson.games.connect.impl.MutableBoard;

import java.util.Random;

/**
 * A totally random inplementation of AI.
 */
public class Dummy extends ArtificialIntelligence {

    private Random random = new Random();

    public Dummy(int color){
        super(color);
    }

    @Override
    public int yourTurn(Board board) {
        MutableBoard myBoard = new MutableBoard(board);

        int x = random.nextInt(board.getWidth());
        boolean canDrop = myBoard.canDropPiece(x);
        System.out.println("Can I drop at " + x + "? " + canDrop);
        if( canDrop ){
            return x;
        } else {
            return -1;
        }
    }
}