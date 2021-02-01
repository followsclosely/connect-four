package net.wilson.games.connect.impl.ai.followsclosely.strategies;

import net.wilson.games.connect.impl.MutableBoard;
import net.wilson.games.connect.impl.ai.followsclosely.Strategy;

import java.util.Random;

public class RandomStrategy implements Strategy {

    private Random random = new Random();

    @Override
    public int yourTurn(MutableBoard board) {
        MutableBoard myBoard = new MutableBoard(board);

        //Select a random place on the board
        int x = random.nextInt(board.getWidth());

        //Keep adding one to the randome spot while canDropPiece is false or we get to the width of the board
        for (int i = 0, width = board.getWidth(); i < width && !myBoard.canDropPiece(x); i++, x = (x + 1) % board.getWidth());

        return x;
    }
}