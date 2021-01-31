package net.wilson.games.connect.ai;

import net.wilson.games.connect.ArtificialIntelligence;
import net.wilson.games.connect.Board;

import java.util.Random;

/**
 * A totally random inplementation of AI.
 */
public class MagnusAI implements ArtificialIntelligence {

    private int color;
    private Random random = new Random();

    public MagnusAI(int color){
        this.color = color;
    }

    public int yourTurn(Board board){
        int x = random.nextInt(board.getWidth());
        boolean canDrop = board.canDropPiece(x);
        System.out.println("Can I drop at " + x + "? " + canDrop);
        if( canDrop ){
            return x;
        } else {
            return -1;
        }
    }
}