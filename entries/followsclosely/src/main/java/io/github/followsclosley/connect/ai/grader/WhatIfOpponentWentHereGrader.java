package io.github.followsclosley.connect.ai.grader;

import io.github.followsclosley.connect.Coordinate;
import io.github.followsclosley.connect.impl.MutableBoard;
import io.github.followsclosley.connect.impl.Turn;
import io.github.followsclosley.connect.impl.TurnUtils;

public class WhatIfOpponentWentHereGrader extends AbstractGrader {

    public WhatIfOpponentWentHereGrader() {
        super(25);
    }

    @Override
    public int score(MutableBoard board, Turn thisTurn, int... opponents) {

        int score = 0;
        int color = board.getPiece(thisTurn.getMove().getX(), thisTurn.getMove().getY());

        //Check out what happens is your opponent places a piece here instead of you
        for (int opponent : opponents) {
            //Undo the last move and replace the piece with your opponent
            Coordinate undo = board.undo();
            board.dropPiece(undo.getX(), opponent);

            Turn turn = TurnUtils.getConnections(board);

            //If your opponent can win its worth -500 points.
            if (turn.isWinner(board.getGoal())) {
                score -= 2000;
            }

            for (Turn.Line line : turn.getLines()) {
                if (line.getPieceCount() == 3 && line.isOpenOnBothEnds()) {
                    score -= 200;
                }
            }

            //Place the board back into the original state
            board.undo();
            board.dropPiece(undo.getX(), color);
        }

        return score;
    }
}