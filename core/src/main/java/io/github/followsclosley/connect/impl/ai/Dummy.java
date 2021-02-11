package io.github.followsclosley.connect.impl.ai;

import io.github.followsclosley.connect.ArtificialIntelligence;
import io.github.followsclosley.connect.Board;
import io.github.followsclosley.connect.impl.MutableBoard;

import java.util.Random;

/**
 * A totally random impl of AI.
 */
public class Dummy implements ArtificialIntelligence {

    private Random random = new Random();

    private int color;

    public Dummy(int color) {
        this.color = color;
    }

    @Override public int getColor() { return color; }

    @Override
    public int yourTurn(Board board) {
        MutableBoard myBoard = new MutableBoard(board);

        //Select a random place on the board
        int x = random.nextInt(board.getWidth());

        //Keep adding one to the randome spot while canDropPiece is false or we get to the width of the board
        for (int i = 0, width = board.getWidth(); i < width; i++, x = (x + 1) % board.getWidth()) {
            if (myBoard.canDropPiece(x)) {
                return x;
            }
        }

        return -1;
    }
}