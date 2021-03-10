package io.github.jaron.connect;

import io.github.followsclosley.connect.ArtificialIntelligence;
import io.github.followsclosley.connect.Board;
import io.github.followsclosley.connect.Coordinate;
import io.github.followsclosley.connect.impl.MutableBoard;
import io.github.followsclosley.connect.impl.TurnUtils;

import java.util.Random;

public class JaronBot implements ArtificialIntelligence {

    private int color;
    private Random random = new Random();

    public JaronBot(int color) {
        this.color = color;
    }

    @Override
    public int getColor() {
        return color;
    }

    public int yourTurn(Board b) {

        MutableBoard board = new MutableBoard(b);
        //If this                                      then  do that
        Coordinate lastTurn = board.getTurns().isEmpty() ? null : board.getTurns().get(board.getTurns().size() - 1);

        //Drop a piece in every spot and see if we win.
        //checks the entire board.
        for (int x = 0; x < board.getWidth(); x++) {
            //if it can then drop piece.
            if (board.canDropPiece(x)) {
                // drops it in the y slot and makes it your color on the x axis.
                int y = board.dropPiece(x, color);

                if (TurnUtils.getConnections(board).hasWinningLine(board.getGoal())) {
                    // then return X that the bot put the number at.
                    return x;
                    // if not the do this statement.
                } else {
                    // the do board undo.
                    board.undo();
                }
            }
        }

        // if last turn was 0 or skipped then....
        if (lastTurn != null) {
            // gets person's last turn.
            int color = board.getPiece(lastTurn.getX(), lastTurn.getY());

            // for width is less than x put a piece at x.
            for (int x = 0, width = board.getWidth(); x < width; x++) {
                // can drop piece then...
                if (board.canDropPiece(x)) {
                    // drop piece.
                    board.dropPiece(x, color);

                    if (TurnUtils.getConnections(board).hasWinningLine(board.getGoal())) {
                        // then return X that the bot put the number at.
                        return x;
                        // if false then do:
                    } else {
                        // the do board undo.                   I don't understand what the thing does, does it clear the entire board or just the turn?
                        board.undo();
                    }
                }

            }
        }

        //I cant win and I am not going to lose, so...
        if (board.getTurns().size() < 5){
            return board.getWidth() / 2;
        }

        //Select a random place on the board
        int x = random.nextInt(board.getWidth());
        //Keep adding one to the random spot while canDropPiece is false or we get to the width of the board
        for (int i = 0, width = board.getWidth(); i < width; i++, x = (x + 1) % board.getWidth()) {
            if (board.canDropPiece(x)) {
                return x;
            }
        }

        return -1;

    }
}
