package io.github.followsclosley.connect.ai.score.grader;

import io.github.followsclosley.connect.impl.MutableBoard;
import io.github.followsclosley.connect.impl.Turn;
import io.github.followsclosley.connect.impl.TurnUtils;

public class WinnerNextTurnGrader extends AbstractGrader {

    public WinnerNextTurnGrader() {
        super(200);
    }

    @Override
    public int score(MutableBoard board, Turn thisTurn, int... opponents) {

        int color = board.getPiece(thisTurn.getMove().getX(), thisTurn.getMove().getY());

        //Check you can win next turn
        int winCount = 0;
        for (int x = 0, width = board.getWidth(); x < width; x++) {
            if (board.canDropPiece(x)) {
                int y = board.dropPiece(x, color);
                Turn turn = TurnUtils.getConnections(board);
                if (turn.hasWinningLine(board.getGoal())) {
                    winCount++;
                }
                board.undo();
            }
        }

        return (winCount > 1) ? winCount * 250 : 0;

    }
}