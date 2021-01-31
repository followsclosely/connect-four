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
    int center;
    int centerrows;
    public int yourTurn(Board board){
        int center = board.getWidth()/2;
        int centerrows = board.getWidth()/3+2;
        int x = random.nextInt(7);
        boolean canDrop = board.canDropPiece(center);
        System.out.println("Can I drop at " + x + "? " + canDrop);
        if( canDrop ){
            return center;
        } else {
            return centerrows;
//            if (centerrows > 3) {
//            } else {
//                return centerrows + 1;
//            }
        }
        }
    }