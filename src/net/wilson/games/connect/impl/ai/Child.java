package net.wilson.games.connect.impl.ai;

import net.wilson.games.connect.ArtificialIntelligence;
import net.wilson.games.connect.Board;
import net.wilson.games.connect.impl.MutableBoard;

import java.util.Random;

/**
 * A totally random inplementation of AI.
 */
public class Child extends ArtificialIntelligence {

    private Random random = new Random();

    public Child(int color){
        super(color);
    }

    @Override
    public int yourTurn(Board b) {
        MutableBoard board = new MutableBoard(b);

        for( int x = 0; x<board.getWidth(); x++){
            if( board.canDropPiece(x)){
                int y = board.dropPiece(x, getColor());
                if( board.getWinner() == getColor() ){

                } else {
                    
                }
            }
        }


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