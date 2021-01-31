package net.wilson.games.connect.ai;

import net.wilson.games.connect.ArtificialIntelligence;
import net.wilson.games.connect.Board;

import java.util.Random;

public class JaronBot implements ArtificialIntelligence {

    private int color;
    private Random random = new Random();

    public JaronBot(int color){
        this.color = color;
    }

    public int yourTurn(Board board){
        int x = random.nextInt(7);
        boolean canDrop = board.canDropPiece(x);
        System.out.println("Can I drop at " + x + "? " + canDrop);
        if( canDrop ){
            return x;
        } else {
            return -1;
        }
    }
}